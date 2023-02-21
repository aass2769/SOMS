package project.soms.mypage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.mypage.service.AttendanceServiceImpl;


@Controller
@RequiredArgsConstructor
public class AttendanceController {
	
	private final AttendanceServiceImpl attendanceServiceImpl;
	
	// 출퇴근 진행 메서드
	@GetMapping("attendance.gotowork")
	@ResponseBody
	public void gotowork(HttpServletRequest req) {
		attendanceServiceImpl.workcheck(req,1);
	}
	
	// 출퇴근 및 다양한 기능에서 페이지를 새로고침할떄 사용
	@GetMapping("attendance.dummy")
	public String attendanceDummy() {
		return "mypage/AttendanceDummy";
	}
	
}
