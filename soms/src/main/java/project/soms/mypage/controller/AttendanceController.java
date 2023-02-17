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
	
	@GetMapping("attendance.gotowork")
	@ResponseBody
	public void gotowork(HttpServletRequest req) {
		attendanceServiceImpl.workcheck(req);
	}
	
	@GetMapping("attendance.dummy")
	public String attendanceDummy() {
		return "mypage/AttendanceDummy";
	}
	
}
