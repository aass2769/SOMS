package project.soms.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@GetMapping("login") // 로그인 페이지
	public String login(HttpServletRequest req, Model model) throws IOException {
		return "login/login";
	}
	
	@PostMapping("login") // 로그인 응답
	public String LoginSubmit(String id, String pw, HttpServletRequest req, HttpServletResponse res, RedirectAttributes rttr) throws IOException {
		// DB에서 ID, PW 체크
		EmployeeDto employee = loginMapper.login(id, pw);
		
		// alert창 설정
		res.setContentType("text/html; charset=utf-8");
		PrintWriter w = res.getWriter();
		
		String location = "";
		
		// DB에서 값이 없을 때 login페이지로, 있을 경우 mypage로 이동
		if(employee == null) {
			w.write("<script>alert('로그인에 실패하셨습니다.');</script>");
			location = "login/login";
		}else {
			req.getSession().setAttribute("LOGIN_EMPLOYEE", employee);
			location = "redirect:/mypage";
		}
		
		return location;
	}

	@GetMapping("logout") // 로그아웃 응답
	public String Logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 세션 파, 괴
		req.getSession().invalidate();
		
		// alert창 설정
		res.setContentType("text/html; charset=utf-8");
		PrintWriter w = res.getWriter();
		w.write("<script>alert('"+"성공적으로 로그아웃이 완료되었습니다."+"');window.close();</script>");
		return "login/login";
	}
	
}
