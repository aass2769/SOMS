package project.soms.mypage.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface AttendanceService {
	
	public String getAttendanceSelectDate(String AttendanceSelectDate);
	
	public List<String> getAttendanceSixMonth();
	
	public String calAttendance(long employeeNo);
	
	public void getTodayAttendanceExistence(long employeeNo, Model model, HttpServletResponse res)throws IOException;
	
}
