package project.soms.mypage.dto;

import lombok.Data;

@Data
public class AttendanceCheckDto {
	private String attendanceGotodate;
	private Integer attendanceGototime;
	private Integer attendanceLeavetotime;
}
