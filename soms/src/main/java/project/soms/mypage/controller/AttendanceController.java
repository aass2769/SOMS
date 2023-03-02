package project.soms.mypage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AttendanceDto;
import project.soms.mypage.repository.AttendanceRepository;

@Controller
@RequiredArgsConstructor
public class AttendanceController {
	
	private final AttendanceRepository attendanceRepository;
	
	// 출퇴근 진행 컨트롤러
	@GetMapping("mypage/attendance")
	@ResponseBody
	public int attendance(HttpServletRequest req, Model model, HttpServletResponse res){
		
		int count = 0;
		
		// 세션의 사원값 가져오기
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		long employeeNo = employee.getEmployeeNo();
		
		// 현재시간값 구해오기
		Date now = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		String date = simpleDateFormat.format(now).substring(0, 10);
		int hour = Integer.parseInt(simpleDateFormat.format(now).substring(11, 13));
		int minute = Integer.parseInt(simpleDateFormat.format(now).substring(14, 16));
		
		if(minute > 30){
			hour += 1;
		}
		
		try {
			// goToWorkCheck = 출근을 했을경우 값이 있고 없을경우엔 값이 "null" 이다.
			String attendnaceCheck = attendanceRepository.attendanceCheck(employeeNo);
			
			// then = 출근 else = 퇴근
			if(attendnaceCheck == null) {
				AttendanceDto attendanceDto = new AttendanceDto(date, hour, employeeNo);
				attendanceRepository.attendanceInsert(attendanceDto);
				count = 1;
			}else {
				AttendanceDto attendanceDto = new AttendanceDto(hour, employeeNo);
				attendanceRepository.attendanceUpdate(attendanceDto);
				count = 2;
			}
			 
		}catch(Exception ex) {
			count = 3;
		}
		
		return count;
	}

}
