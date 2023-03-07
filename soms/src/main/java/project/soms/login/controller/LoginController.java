package project.soms.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.login.repository.mapper.LoginMapper;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginMapper loginMapper;
	
	@GetMapping("login")
	public String loginform(Model model, RedirectAttributes rttr, String message) {
		
		if(message!= null) {
			model.addAttribute("message", message);
		}
		
		return "login/login";
	}
	
	@PostMapping("logincheck")
	public String logincheck(String id, String pw, HttpServletRequest req, RedirectAttributes rttr) {
		// DB에서 ID, PW 체크
		EmployeeDto employee = loginMapper.login(id, pw);
		
		String location = "";
		
		if(employee == null) {
			rttr.addAttribute("message", "아이디와 비밀번호가 부합하지 않습니다.");
			location = "redirect:/login";
		}else {
			req.getSession().setAttribute("LOGIN_EMPLOYEE", employee);
			location = "redirect:/mypage";
		}
		
		return location;
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest req, RedirectAttributes rttr) {
		req.getSession().invalidate();
		rttr.addAttribute("message", "로그아웃이 완료되었습니다.");
		return "redirect:/login";
	}
	
}
