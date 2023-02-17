package project.soms.mypage.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.mypage.dto.AttendanceCheckDto;
import project.soms.mypage.dto.WorkDto;
import project.soms.mypage.repository.mapper.AttendanceMapper;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
	
	private final AttendanceMapper attendanceMapper;
	
	public String goToWorkCheck(long employeeNo){
		return attendanceMapper.workcheck(employeeNo);
	}
	
	public void goToWork(WorkDto goToWorkDto) {
		attendanceMapper.goToWork(goToWorkDto);
	}
	
	public void leaveToWork(WorkDto leaveToWorkDto) {
		attendanceMapper.leaveToWork(leaveToWorkDto);
	}
	
	public long getAttendanceNum(long employeeNo) {
		return attendanceMapper.getAttendanceNum(employeeNo);
	}
	
	public List<AttendanceCheckDto> attendanceCheck(long employeeNo, String ym){
		return attendanceMapper.attendanceCheck(employeeNo,ym);
	}
	
	public AttendanceCheckDto getWorkTime(long employeeNo, String workDate) {
		return attendanceMapper.getWorkTime(employeeNo, workDate);
	}
}
