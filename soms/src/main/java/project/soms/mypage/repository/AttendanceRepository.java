package project.soms.mypage.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.mypage.dto.AttendanceDto;
import project.soms.mypage.repository.mapper.AttendanceMapper;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
	
	private final AttendanceMapper attendanceMapper; 
	
	// 출근했는지 안했는지 체크
	public String attendanceCheck(long employeeNo) {
		return attendanceMapper.attendanceCheck(employeeNo);
	}
	
	// 출근
	public void attendanceInsert(AttendanceDto attendanceDto) {
		attendanceMapper.attendanceInsert(attendanceDto);
	}
	
	// 퇴근
	public void attendanceUpdate(AttendanceDto attendanceDto) {
		attendanceMapper.attendanceUpdate(attendanceDto);
	}
	
	// 출퇴근 기록
	public List<AttendanceDto> attendanceList(long employeeNo, String selectDate){
		return attendanceMapper.attendanceList(employeeNo, selectDate);
	}
	
	// 주간근로 계산
	public AttendanceDto calAttendance(long employeeNo, String today) {
		return attendanceMapper.calAttendance(employeeNo, today);
	}
	
	// 오늘 출퇴근 유무
	public Integer getTodayAttendanceExistence(long employeeNo, String today) {
		return attendanceMapper.getTodayAttendanceExistence(employeeNo, today);
	}
	
	// 최근 출퇴근 기록 첫번쨰 가져오기
	public AttendanceDto getAttendanceRecent(long employeeNO) {
		return attendanceMapper.getAttendanceRecent(employeeNO);
	}
	
	
}
