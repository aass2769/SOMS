package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.mypage.dto.WorkDto;

@Mapper
public interface AttendanceMapper {

	String workcheck(long employeeNo);
	
	void goToWork(WorkDto goToWorkDto);
	
	void leaveToWork(WorkDto leaveToWorkDto);

	long getAttendanceNum(long employeeNo);
	
	List<String> attendanceCheck(@Param("employeeNo")long employeeNo, @Param("ym")String ym);
}
