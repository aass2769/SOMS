package project.soms.login.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.login.repository.mapper.LoginMapper;
import project.soms.login.service.LoginServiceImpl;
import project.soms.mypage.dto.ManageDto;
import project.soms.mypage.repository.mapper.MypageMapper;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginMapper loginMapper;
	
	private final LoginServiceImpl loginServiceImpl;
	
	private final MypageMapper mypageMapper;
	
	@PostMapping("login")
	public String Login(String id, String pw, HttpServletRequest req) {
		EmployeeDto employee = loginMapper.LoginCheck(id, pw);
		return loginServiceImpl.LoginGo(employee, req);
	}
	
	@GetMapping("login.success")
	public String LoginSucess(Model model, HttpServletRequest req) {
		
		
		List<ManageDto> manages = mypageMapper.getManages();
		model.addAttribute("manages", manages);
		
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("employee");
		model.addAttribute("employee", employee);
		
		return "mypage/layout/employee";
	}
	
}
