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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import project.soms.email.dto.EmailDto;
import software.amazon.awssdk.utils.StringUtils;

import javax.mail.*;
import javax.mail.search.MessageNumberTerm;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository{

  /**
   * JavaMailSender와 MailProperties 객체 생성
   */
  private final JavaMailSender javaMailSender;
  private final MailProperties mailProperties;

  /**
   * emailMap에 이매일 내역과 해당 값들 전체 저장
   * emailDetail에서 기준값으로 해당 이메일의 상세 내역 select
   * 기준값은 long 타입으로 선언하여 메일 내역에 1씩 증가하여 값 할당
   */
  private static Map<Long, EmailDto> emailMap = new TreeMap<>(Collections.reverseOrder());

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
      /**
       * imap 주소
       * 임직원 계정 (임직원 Id + 도메인), 임직원 비밀번호(공통값으로 설정)
       */
      store.connect("imap.mail.us-east-1.awsapps.com", "admin@somsolution.awsapps.com", employeePw);

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
        //한글 이름이 깨지지 않도록 parsing
        email.setEmailFrom(krStringParsing(message.getFrom()[0].toString()));
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
            if (bodyPart.getContentType().contains("TEXT/PLAIN")) {
              sb.append(bodyPart.getContent());
            }

            //bodytype에 값들 중 첨부파일 (ATTACHMENT) 있는지 확인
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || StringUtils.isNotBlank(bodyPart.getFileName())) {
              InputStream is = bodyPart.getInputStream();

              //첨부파일을 프로젝트의 디렉토리에 저장
              String fileName = krStringParsing(bodyPart.getFileName());
              String extension = FilenameUtils.getExtension(fileName);
              String newFileName = UUID.randomUUID().toString() + "." + extension;
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

    } catch (MessagingException e) {
      log.error("error={}", e);
      throw new RuntimeException(e);
    } catch (IOException e) {
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
    /**
     * 일단 테스트를 위해 계정 절대값 설정
     */
    employeeId = "admin";

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", employeeId + "@somsolution.awsapps.com", employeePw);

      Folder emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_WRITE);

      Message message = emailFolder.getMessage(Math.toIntExact(emailNo));

      Flags flags = message.getFlags();

      flags.add(Flags.Flag.SEEN);

      message.setFlags(flags, true);

    } catch (NoSuchProviderException e) {
      throw new RuntimeException(e);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  //이메일 삭제시 휴지통으로 이동
  @Override
  public void moveToTrash(String employeeId, String employeePw, String folderName, List<Long> emailNoList) {

    //설정 객체 생성 후 필요 값 할당
    Properties properties = new Properties();
    //메일 선언을 imap으로
    properties.put("mail.store.protocol", "imaps");
    //해당 설정을 이메일 세션에 저장
    Session emailSession = Session.getInstance(properties);

    try {
      Store store = emailSession.getStore();
      store.connect("imap.mail.us-east-1.awsapps.com", "admin" + "@somsolution.awsapps.com", employeePw);

      Folder emailFolder = store.getFolder(folderName);
      emailFolder.open(Folder.READ_WRITE);

      Folder trashFolder = store.getFolder("Trash");
      trashFolder.open(Folder.READ_WRITE);

      for (Long emailNo : emailNoList) {

        // 이메일을 찾기 위해 검색 조건 설정
        MessageNumberTerm searchTerm = new MessageNumberTerm(Math.toIntExact(emailNo));
        Message[] foundMessages = emailFolder.search(searchTerm);

        if (foundMessages.length == 0) {
          log.warn("이메일을 찾을 수 없음");
        } else {
          for (Message message : foundMessages) {
            // 휴지통으로 이메일을 복사
            emailFolder.copyMessages(new Message[] {message}, trashFolder);
            // 기존 메일함에서 메일을 삭제
            message.setFlag(Flags.Flag.DELETED, true);
            log.warn("이메일 휴지통으로 이동 완료");
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
   * 한글이 base64의 패턴화 된걸 한들로 재변경
   */
  private String krStringParsing(String krString) {

    String pattern = "=\\?(.*?)\\?(.*?)\\?(.*?)\\?=";

    StringBuilder buffer = new StringBuilder();

    String charsetMain = "UTF-8";
    String charsetSub = "B";

    Pattern p = Pattern.compile(pattern);
    Matcher matcher = p.matcher(krString);

    while (matcher.find()) {
      charsetMain = matcher.group(1);
      charsetSub = matcher.group(2);
      try {
        buffer.append(new String(Base64.decode(matcher.group(3)), charsetMain));
      } catch (Base64DecodingException e) {
        log.error("error={}", e);
        throw new RuntimeException(e);
      } catch (UnsupportedEncodingException e) {
        log.error("error={}", e);
        throw new RuntimeException(e);
      }
    }

    if (buffer.toString().isEmpty()) {
      buffer.append(krString);
    }

    return buffer.toString();
  }
}
