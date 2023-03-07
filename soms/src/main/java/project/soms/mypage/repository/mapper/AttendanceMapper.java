package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.mypage.dto.AttendanceDto;

@Mapper
public interface AttendanceMapper {
	
	// 출근 했는지 안했는지 체크
	public String attendanceCheck(long employeeNo);
	
	// 출근
	public void attendanceInsert(AttendanceDto attendanceDto);
	
	// 퇴근
	public void attendanceUpdate(AttendanceDto attendanceDto);
	
	// 출퇴근 기록
	public List<AttendanceDto> attendanceList(@Param("employeeNo") long employeeNo, @Param("selectDate") String selectDate);
	
	// 주간근로 계산
	public AttendanceDto calAttendance(@Param("employeeNo") long employeeNo, @Param("today") String today);
	
	// 오늘 출퇴근 유무
	public Integer getTodayAttendanceExistence(@Param("employeeNo") long employeeNo, @Param("today") String today);
	
	public AttendanceDto getAttendanceRecent(long employeeNo1);
}
