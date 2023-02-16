package project.soms.mypage.dto;

import lombok.Data;

@Data
public class WorkDto {
	
	private long employeeNo;
	
	private long attendanceNo;
	
	private String nowDate;
	private Integer nowHour;
	
	
	
	public WorkDto(long employeeNo, String nowDate, Integer nowHour) {
		this.employeeNo = employeeNo;
		this.nowDate = nowDate;
		this.nowHour = nowHour;
	}

	public WorkDto(long employeeNo, Integer nowHour, long attendanceNo) {
		this.employeeNo = employeeNo;
		this.nowHour = nowHour;
		this.attendanceNo = attendanceNo;
	}



}
