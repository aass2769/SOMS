package project.soms.login.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.soms.employee.dto.EmployeeDto;
import project.soms.login.repository.mapper.LoginMapper;
import project.soms.login.service.LoginServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginMapper loginMapper;
	
	private final LoginServiceImpl loginServiceImpl;

	
	@GetMapping("login")
	public String login() {
		return "login/login";
	}
	
	@PostMapping("login")
	public String Login(String id, String pw, HttpServletRequest req) {
		EmployeeDto employee = loginMapper.LoginCheck(id, pw);
		return loginServiceImpl.LoginGo(employee, req);
	}
	
	@GetMapping("login.success")
	public String LoginSucess(Model model, HttpServletRequest req) {
		
		// 세션에 employee 담기
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		model.addAttribute("LOGIN_EMPLOYEE", employee);
		
		return "error";
	}
	
}
