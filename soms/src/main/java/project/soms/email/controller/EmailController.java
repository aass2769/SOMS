package project.soms.email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.email.dto.EmailDto;
import project.soms.email.service.EmailService;

import javax.servlet.http.HttpServletRequest;
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

  @GetMapping("emailList")
  public String emailList(HttpServletRequest request, Model model) {
    List<EmailDto> emailList = emailService.emailList(request);
    model.addAttribute("emailLIst", emailList);

    String folderName = request.getParameter("folderName");
    String location = "";
    if (folderName.equals("inbox")) {
      location = "email/getMailRepository";
    } else if (folderName.equals("Sent Items")) {
      location = "email/sendMailRepository";
    } else if (folderName.equals("Trash")) {
      location = "email/wasteBasket";
    } else if (folderName.equals("outBox")) {
      location = "email/outBox";
    }
    return location;
  }

  @GetMapping("emailDetail")
  public String emailDetail(Long emailNo, Model model) {
    EmailDto emailDetail = emailService.emailDetail(emailNo);
    model.addAttribute("emailDetail", emailDetail);
    return "email/readMail";
  }

  @PostMapping("moveToTrash")
  public String moveToTrash(HttpServletRequest request) {
    emailService.moveToTrash(request);

    return "email/wasteBasket";
  }
}
