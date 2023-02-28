package project.soms.email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.soms.email.dto.EmailDto;
import project.soms.email.service.EmailService;
import project.soms.employee.dto.EmployeeDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

  @GetMapping("readMail")
  public String readmail(Model model) {
    model.addAttribute("title", "제목");
    model.addAttribute("article", "내용");
    return "email/emailForm/readMail";
  }


  @GetMapping("emailList")
  public String emailList(HttpServletRequest request, Model model) {
    List<EmailDto> emailList = emailService.emailList(request);
    model.addAttribute("emailList", emailList);

    String folderName = request.getParameter("folderName");
    String location = "";
    if (folderName.equals("INBOX")) {
      location = "email/mailFolder/getMailRepository";
    } else if (folderName.equals("Sent Items")) {
      location = "email/mailFolder/sendMailRepository";
    } else if (folderName.equals("Trash")) {
      location = "email/mailFolder/wasteBasket";
    } else if (folderName.equals("outBox")) {
      location = "email/mailFolder/outBox";
    }
    return location;
  }

  @GetMapping("emailDetail")
  public String emailDetail(Long emailNo, Model model, HttpServletRequest request) {
    EmailDto emailDetail = emailService.emailDetail(emailNo);
    emailService.emailUpdateSeen(request, emailNo);
    model.addAttribute("employee", getEmployee(request));
    model.addAttribute("emailDetail", emailDetail);
    return "email/emailForm/readMail";
  }

  @PostMapping("moveToTrash")
  public String moveToTrash(HttpServletRequest request, @RequestParam List<Long> emailNoList) {
    emailService.moveToTrash(request, emailNoList);
    return "email/mailFolder/wasteBasket";
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
