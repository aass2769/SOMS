package project.soms.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.mypage.repository.mapper.MypageMapper;

@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageMapper mypageMapper;
	
	@PostMapping("change.password")
	@ResponseBody
	public void changePassword(String pw, long no) { 
		mypageMapper.changePassword(pw, no);
	}
	
	@PostMapping("change.email")
	@ResponseBody
	public void changeEmail(String email, long no) {
		mypageMapper.changeEmail(email, no);
	}
	
	@PostMapping("change.emailpw")
	@ResponseBody
	public void changeEmailPw(String emailpw, long no) {
		mypageMapper.changeEmailPw(emailpw, no);
	}

}
