package project.soms.mypage.dto;

import lombok.Data;

@Data
public class OvertimeDto {
	private String proposerEmployeeNo;
	private Integer submissionStatus;
	private String approverEmployeeNo;
	
	private String overtimeStartDate;
	private String overtimeEndDate;
	
	private Integer overtimeStartTime;
	private Integer overtimeEndTime;
}
