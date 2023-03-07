package project.soms.email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.soms.email.dto.EmailDto;
import project.soms.email.service.EmailService;
import project.soms.employee.dto.EmployeeDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {

  private final EmailService emailService;

  @GetMapping("getMail")
  public String mailmain(Model model) {
    model.addAttribute("tilte", "제목");
    model.addAttribute("article", "내용");
    return "email/getMailRepository";
  }

  @GetMapping("sendForm")
  public String readmail(Model model) {
    model.addAttribute("emailDto", new EmailDto());
    return "email/emailForm/sendMail";
  }

  @GetMapping("reply")
  public String reply(Long emailNo, Model model) {
    EmailDto emailDto = new EmailDto();

    EmailDto emailDetail = emailService.emailDetail(emailNo);
    emailDto.setEmailRecipient(emailDetail.getEmailRecipient());

    model.addAttribute("emailDto", emailDto);
    return "email/emailForm/sendMail";
  }

  @GetMapping("forward")
  public String forward(Long emailNo, Model model) {

    EmailDto emailDetail = emailService.emailDetail(emailNo);
    emailDetail.setEmailRecipient(new ArrayList<>());

    model.addAttribute("emailDto", emailDetail);
    return "email/emailForm/sendMail";
  }


  @GetMapping("emailList")
  public String emailList(HttpServletRequest request, Model model) {
    List<EmailDto> emailList = emailService.emailList(request);
    model.addAttribute("emailList", emailList);

    String folderName = request.getParameter("folderName");
    model.addAttribute("folderName", folderName);

    return "email/mailFolder/getMailRepository";
  }

  @GetMapping("emailDetail")
  public String emailDetail(Long emailNo, Model model, HttpServletRequest request) {
    EmailDto emailDetail = emailService.emailDetail(emailNo);
    emailService.emailUpdateSeen(request, emailNo);
    model.addAttribute("employee", getEmployee(request));
    model.addAttribute("emailDetail", emailDetail);
    return "email/emailForm/readMail";
  }

  @PostMapping("moveToTrashOrJunk")
  public String moveToTrashOrJunk(HttpServletRequest request, @RequestParam List<Long> emailNoList) {
    emailNoList.removeIf(emailNo -> emailNo.equals(0L));
    if (emailNoList.size() <= 0) {
      return "redirect:" + request.getHeader("Referer");
    }
    if (request.getParameter("folderName").equals("Trash") || request.getParameter("folderName").equals("Junk E-mail")) {
      emailService.deleteMessage(request, emailNoList);
    } else {
      emailService.moveToTrashOrJunk(request, emailNoList);
    }
    return "redirect:" + request.getHeader("Referer");
  }

  @ResponseBody
  @GetMapping("emailSend")
  public String emailSend(HttpServletRequest request, EmailDto emailDto, @RequestParam("recipients") List<String> recipients, @RequestParam("fileName") List<String> fileName) {

    emailDto.setEmailRecipient(recipients);
    emailDto.setEmailAttachment(fileName);


    try {
      emailService.emailSend(emailDto, getEmployee(request));
    } catch (MailSendException e) {
      return "test실패화면";
    } catch (FileNotFoundException e) {
      return "파일실패";
    }

    return "test";
  }

  @GetMapping("downloadAttachment")
  public ResponseEntity<ByteArrayResource> downloadAttachment(@RequestParam("fileName") String fileName) {
    return emailService.downloadAttachment(fileName);
  }

  private EmployeeDto getEmployee(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");
  }

}
