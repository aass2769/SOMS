package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.mypage.dto.AttendanceCheckDto;
import project.soms.mypage.dto.WorkDto;

@Mapper
public interface AttendanceMapper {
	
	// 출근인지 퇴근인지를 체크
	String workcheck(long employeeNo);
	// 출근 Insert
	void goToWork(WorkDto goToWorkDto);
	// 퇴근 update
	void leaveToWork(WorkDto leaveToWorkDto);
	// 퇴근할 메서드에서 조건으로 사용할 출근한 attendance primary  key
	long getAttendanceNum(long employeeNo);
	// 출근퇴근한 목록을 리스트로 가져온다
	List<AttendanceCheckDto> attendanceCheck(@Param("employeeNo")long employeeNo, @Param("ym")String ym);
	// 출퇴근한 목록을 최근 한개 져온다. 
	AttendanceCheckDto getWorkTime(@Param("employeeNo")long employeeNo, @Param("workDate")String workDate);
}
