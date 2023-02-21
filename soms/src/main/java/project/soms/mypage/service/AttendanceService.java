package project.soms.mypage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import project.soms.mypage.dto.AttendanceCheckDto;
import project.soms.mypage.dto.WorkDto;

public interface AttendanceService {
	
	void workcheck(HttpServletRequest req, Integer value);
	
	void goToWork(WorkDto goToWorkDto);
	
	void leaveToWork(WorkDto leaveToWorkDto);
	
	long getAttendanceNum(long employeeNo);
	
	List<AttendanceCheckDto> attendanceCheck(long employeeNo,String ym);
	
	String getAttendanceDate(String attendanceDate);
	
	List<String> getAttendanceAtSixMonths();
	
	AttendanceCheckDto getWorkTime(long employeeNo, String workDate);
	
	String getWeekWorkTime(long employeeNo);
}
