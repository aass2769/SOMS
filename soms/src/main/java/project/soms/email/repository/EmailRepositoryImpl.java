package project.soms.email.repository;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import project.soms.email.dto.EmailDto;
import project.soms.employee.dto.EmployeeDto;
import software.amazon.awssdk.utils.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.MessageNumberTerm;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.org.apache.xml.internal.security.Init.init;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository{

  /**
   * JavaMailSender와 MailProperties 객체 생성
   */
  private final JavaMailSender javaMailSender;
  private final MailProperties mailProperties;

  /*
   * emailMap에 이매일 내역과 해당 값들 전체 저장
   * emailDetail에서 기준값으로 해당 이메일의 상세 내역 select
   * 기준값은 long 타입으로 선언하여 메일 내역에 1씩 증가하여 값 할당
   */
  private static final Map<Long, EmailDto> emailMap = new TreeMap<>(Collections.reverseOrder());

  //이메일 내역
  @Override
  public List<EmailDto> emailList(String employeeId, String employeePw, String folderName) {

    //현재 map에 저장된 값 초기화
    emailMap.clear();

    //반환할 리스트 타입 초기화
    List<EmailDto> emailList = new ArrayList<>();

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getDefaultInstance(properties);
    try {
      //이메일 세션에 값을 스토어에 저장
      Store store = emailSession.getStore();
      /*
        imap 주소
        임직원 계정 (임직원 Id + 도메인), 임직원 비밀번호(공통값으로 설정)
       */
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.com", employeePw);

      //폴더이름으로 불러올 폴더 설정
      Folder emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_ONLY);

      //폴더 안에 메세지 수량만큼 반복
      for (int i = 1; i <= emailFolder.getMessageCount(); i++) {
        //폴더 안에 내용을 메세기 객체로 불러옴
        Message message = emailFolder.getMessage(i);
        //EmailDto 클래스에 해당 값 주입
        EmailDto email = new EmailDto();
        email.setEmailNo(Long.valueOf(message.getMessageNumber()));
        email.setEmailSubject(message.getSubject());

        int addressPoint1 = message.getFrom()[0].toString().indexOf("<");
        int addressPoint2 = message.getFrom()[0].toString().indexOf(">");
        String subString;
        if (addressPoint1 >= 0) {
          subString = message.getFrom()[0].toString().substring(addressPoint1 + 1, addressPoint2);
          email.setEmailFrom(subString);
        } else {
          email.setEmailFrom(message.getFrom()[0].toString());
        }

        List<String> recipients = new ArrayList<>();
        if (message.getAllRecipients().length > 0) {
          for (int j = 0; j < message.getAllRecipients().length; j++) {
            int trimPoint1 = message.getAllRecipients()[j].toString().indexOf("<");
            int trimPoint2 = message.getAllRecipients()[j].toString().indexOf(">");
            String subString2;
            if (addressPoint1 >= 0) {
              subString2 = message.getAllRecipients()[j].toString().substring(trimPoint1 + 1, trimPoint2);
              recipients.add(subString2);
            } else {
              recipients.add(String.valueOf(message.getAllRecipients()[j]));
            }
          }
        }
        email.setEmailRecipient(recipients);
        //날짜를 simpleFormat으로 parsing
        email.setEmailSentDate(dateParsing(message.getSentDate()));
        //읽은 메일인지 아닌지 boolean값으로 저장 / true일때 읽은 상태
        Flags flags = message.getFlags();
        email.setEmailSeen(flags.contains(Flags.Flag.SEEN));

        //내용을 오브젝트로 선언
        Object content = message.getContent();

        //내용이 값들이 전체 String일 때 해당 내용의 값을 바로 dto클래스에 할당
        if (content instanceof String) {
          email.setEmailContent((String) content);

          //여러 값이 있을 때
        } else if (content instanceof Multipart) {

          //메일 내용을 여러 Multipart 객체에
          Multipart multipart = (Multipart) content;
          StringBuilder sb = new StringBuilder();

          //EmailDto 에 파일 이름 리스트에 담기 위한 리스트를 선언
          List<String> attachmentFileName = new ArrayList<>();
          List<String> attachment = new ArrayList<>();

          //content에 담긴 값만큼 반복
          for (int j = 0; j < multipart.getCount(); j++) {

            //multipart에 들어간 bodypart를 bodypart객체로
            BodyPart bodyPart = multipart.getBodyPart(j);

            //bodytype에 값들 중 'TEXT/PLAIN'으로 설정된 값은 String으로
            if (bodyPart.getContentType().contains("text/plain;") || bodyPart.getContentType().contains("text/html")) {
              String contents = (String) bodyPart.getContent();
              sb.append(contents);
            }

            //bodytype에 값들 중 첨부파일 (ATTACHMENT) 있는지 확인
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || StringUtils.isNotBlank(bodyPart.getFileName())) {
              InputStream is = bodyPart.getInputStream();

              //첨부파일을 프로젝트의 디렉토리에 저장
              String fileName = krStringParsing(bodyPart.getFileName());
              String extension = FilenameUtils.getExtension(fileName);
              String newFileName = UUID.randomUUID() + "." + extension;
              Files.createDirectories(Paths.get("src/main/resources/static/files"));
              Files.copy(is, Paths.get("src/main/resources/static/files/" + newFileName));
              is.close();

              //첨부파일 여부를 true로
              email.setEmailHasAttachment(true);

              //다운로드로 보낼 filename과 화면에 나타낼 filename
              attachmentFileName.add(newFileName);
              attachment.add(fileName);
            }
          }
          //EmailDto에 내용과 첨부파일 값 저장
          email.setEmailContent(sb.toString());
          email.setEmailAttachmentFileName(attachmentFileName);
          email.setEmailAttachment(attachment);
        }
        //기준값 및 로그인 emailMap에 저장
        emailMap.put(email.getEmailNo(), email);
      }
      emailFolder.close();
      store.close();

      //반환해줄 리스트 생성 후 emailMap의 값 주입
      emailList = new ArrayList<>(emailMap.values());

    } catch (MessagingException | IOException e) {
      log.error("error={}", e);
      throw new RuntimeException(e);
    }
    return emailList;
  }

  //이메일 선택시 해당 번호의 이메일 값
  @Override
  public EmailDto emailDetail(Long emailNo) {
    //map에서 기준값에 맞는 DTO클래스 반환
    return emailMap.get(emailNo);
  }

  @Override
  public void emailUpdateSeen(String employeeId, String employeePw, String folderName, Long emailNo) {

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.com", employeePw);

      Folder emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_WRITE);

      Message message = emailFolder.getMessage(Math.toIntExact(emailNo));

      Flags flags = message.getFlags();

      flags.add(Flags.Flag.SEEN);

      message.setFlags(flags, true);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  //이메일 삭제시 휴지통으로 이동
  @Override
  public void moveToTrashOrJunk(String employeeId, String employeePw, String folderName, String moveFolder, List<Long> emailNoList) {

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.com", employeePw);

      Folder emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_WRITE);


      Folder moveFolderName = null;
      if (moveFolder.equals("Trash")) {
        log.info("휴지통 폴더 생성");
        moveFolderName = store.getFolder("Trash");
      } else if (moveFolder.equals("Junk E-mail")) {
        log.info("스팸메일함 폴더 생성");
        moveFolderName = store.getFolder("Junk E-mail");
      }

      moveFolderName.open(Folder.READ_WRITE);

      for (Long emailNo : emailNoList) {

        // 이메일을 찾기 위해 검색 조건 설정
        MessageNumberTerm searchTerm = new MessageNumberTerm(Math.toIntExact(emailNo));
        Message[] foundMessages = emailFolder.search(searchTerm);

        if (foundMessages.length == 0) {
          log.warn("이메일을 찾을 수 없음");
        } else {
          for (Message message : foundMessages) {
            // 휴지통으로 이메일을 복사
            emailFolder.copyMessages(new Message[] {message}, moveFolderName);
            // 이메일을 삭제하면 휴지통으로 이동됨
            message.setFlag(Flags.Flag.DELETED, true);
            log.warn("이메일 이동 완료");
          }
        }
      }

      emailFolder.close(true);
      store.close();
    } catch (MessagingException e) {
      log.error("error={}", e);
      e.printStackTrace();
    }
  }

  @Override
  public void emailSend(EmailDto emailDto, EmployeeDto employee, String employeePw) throws FileNotFoundException {

    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper msg = new MimeMessageHelper(message, true, "utf-8");
      InternetAddress[] address = new InternetAddress[emailDto.getEmailRecipient().size()];
      int i = 0;
      for (String addressList : emailDto.getEmailRecipient()) {
        address[i] = new InternetAddress(addressList);
        i += 1;
      }
      //InternetAddress fromAddress = new InternetAddress(employee.getEmployeeId() + "@somsolution.com");
      msg.setFrom(employee.getEmployeeId() + "@somsolution.com", MimeUtility.encodeText(employee.getEmployeeName(), "utf-8", "B"));
      msg.setTo(address);
      msg.setSubject(emailDto.getEmailSubject());

      MimeMultipart messageContent = new MimeMultipart("mixed");

      MimeBodyPart textWarp = new MimeBodyPart();
      textWarp.setContent(emailDto.getEmailContent(), "text/plain; charset=utf-8");
      messageContent.addBodyPart(textWarp);
      try {
        if (emailDto.getEmailAttachmentFileName().size() > 0) {
          for (int j = 0; j < emailDto.getEmailAttachmentFileName().size(); j++) {
            FileDataSource files = new FileDataSource(emailDto.getEmailAttachmentFileName().get(j));
            File file = new File(emailDto.getEmailAttachmentFileName().get(j));
            if (file.exists()) {
              MimeBodyPart attachmentWarp = new MimeBodyPart();
              attachmentWarp.setDataHandler(new DataHandler(files));
              attachmentWarp.setFileName(emailDto.getEmailAttachment().get(j));
              messageContent.addBodyPart(attachmentWarp);
              log.info("첨부파일 저장");
            } else {
              log.error("fileName={}", emailDto.getEmailAttachmentFileName().get(j));
              log.error("파일 명칭 불량");
              message.setContent(messageContent);
              saveDraftEmail(message, employee.getEmployeeId(), employeePw);
              throw new FileNotFoundException();
            }
          }
        }
      } catch (NullPointerException e) {
        log.info("첨부파일 없음");
      }

      message.setContent(messageContent);

      javaMailSender.send(message);

    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    } catch (MailSendException e) {
      saveDraftEmail(message, employee.getEmployeeId(), employeePw);
      throw new RuntimeException(e);
    }

    log.info("이매일 전송 완료");

  }

  private void saveDraftEmail(Message message, String employeeId, String employeePw) {

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.com", employeePw);

      Folder emailFolder = store.getFolder("Drafts");
      emailFolder.open(Folder.READ_WRITE);


      //보관함에 저장
      emailFolder.appendMessages(new Message[]{message});
      message.setFlag(Flags.Flag.DELETED, true);
      log.warn("임시보관함에 저장");

      emailFolder.close(true);
      store.close();
    } catch (MessagingException ex) {
      throw new RuntimeException(ex);
    }

  }

  //이매일 영구삭제
  @Override
  public void deleteMessage(String employeeId, String employeePw, String folderName, List<Long> emailNoList) {

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.com", employeePw);

      Folder emailFolder = null;
      if (folderName.equals("Trash")) {
        log.info("휴지통 폴더 생성");
        emailFolder = store.getFolder("Trash");
      } else if (folderName.equals("Junk E-mail")) {
        log.info("스팸메일함 폴더 생성");
        emailFolder = store.getFolder("Junk E-mail");
      }

      assert emailFolder != null;
      emailFolder.open(Folder.READ_WRITE);

      for (Long emailNo : emailNoList) {

        // 이메일을 찾기 위해 검색 조건 설정
        MessageNumberTerm searchTerm = new MessageNumberTerm(Math.toIntExact(emailNo));
        Message[] foundMessages = emailFolder.search(searchTerm);

        if (foundMessages.length == 0) {
          log.info("이메일을 찾을 수 없음");
        } else {
          for (Message message : foundMessages) {
            // 이메일을 삭제하면 휴지통으로 이동됨
            message.setFlag(Flags.Flag.DELETED, true);
            log.info("이메일 영구삭제 완료");
          }
        }
      }

      emailFolder.close(true);
      store.close();
    } catch (MessagingException e) {
      log.error("error={}", e);
      e.printStackTrace();
    }
  }



  @Override
  public ResponseEntity<ByteArrayResource> downloadAttachment(String emailFileName) {
    try {
      File file = new File("src/main/resources/static/files/" + emailFileName);
      byte[] data = FileUtils.readFileToByteArray(file);
      ByteArrayResource resource = new ByteArrayResource(data);

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .contentLength(file.length())
          .body(resource);

    } catch (IOException e) {
      log.error("error={}", e);
      throw new RuntimeException(e);
    }

  }

  /**
   * 'sentDate'를 Datetime type으로 변경
   * 기본 'dow mon dd hh:mm:ss yyyy' -> 'yyyy-MM-dd HH:mm:ss'
   */
  private String dateParsing(Date emailSentDate) {
    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return datetimeFormat.format(emailSentDate);
  }

  /**
   * 발신자 및 파일 이름등의 한글이 깨지지 않도록
   * 한글이 base64의 패턴화 된걸 한글로 재변경
   */
  private String krStringParsing(String krString) {

    String pattern = "=\\?(.*?)\\?(.*?)\\?(.*?)\\?=";

    StringBuilder buffer = new StringBuilder();

    String charsetMain = "utf-8";
    String charsetSub = "B";

    Pattern p = Pattern.compile(pattern);
    if (krString != null) {
      Matcher matcher = p.matcher(krString);

      while (matcher.find()) {
        charsetMain = matcher.group(1);
        charsetSub = matcher.group(2);
        try {
          init();
          buffer.append(new String(Base64.decode(matcher.group(3)), charsetMain));
        } catch (Base64DecodingException | UnsupportedEncodingException e) {
          log.error("error={}", e);
          throw new RuntimeException(e);
        }
      }

    }
    return buffer.toString();
  }
}
