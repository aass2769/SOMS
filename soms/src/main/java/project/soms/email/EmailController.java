package project.soms.email;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EmailController {

	 @GetMapping("getMail")
	  public String mailmain(Model model) {
		  model.addAttribute("tilte", "제목");
		  model.addAttribute("article", "내용");
		  return "email/getMailRepository";
	  }
	  @GetMapping("sendMailRepository")
	  public String sendMailRepository(Model model) {
		  model.addAttribute("tilte", "제목");
		  model.addAttribute("article", "내용");
		  return "email/sendMailRepository";
	  }
	  @GetMapping("outBox")
	  public String outBox(Model model) {
		  model.addAttribute("tilte", "제목");
		  model.addAttribute("article", "내용");
		  return "email/outBox";
	  }
	  @GetMapping("wasteBasket")
	  public String wasteBasket(Model model) {
		  model.addAttribute("tilte", "제목");
		  model.addAttribute("article", "내용");
		  return "email/wasteBasket";
	  }
	  @GetMapping("sendMail")
	  public String sendMail(Model model) {
		  model.addAttribute("tilte", "제목");
		  model.addAttribute("article", "내용");
		  return "email/sendMail";
	  }
	  @GetMapping("readMail")
	  public String readmail(Model model) {
		  model.addAttribute("title", "제목");
		  model.addAttribute("article", "내용");
		  return "email/readMail";
	  }
}
