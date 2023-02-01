package project.soms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontController {

  @GetMapping("test1")
  public String test1() {
    return "member/member";
  }

  @ResponseBody
  @GetMapping("test2")
  public String test2() {
    return "컨트롤러 테스트";
  }
}
