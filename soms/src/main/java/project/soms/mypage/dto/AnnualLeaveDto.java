package project.soms.mypage.dto;

import lombok.Data;

@Data
public class AnnualLeaveDto {
	private String proposerEmployeeNo;
	private Integer submissionStatus;
	private String approverEmployeeNo;
	private Integer dateDiff;
	private String annualLeaveSection;
	private String annualLeaveStart;
	private String annualLeaveEnd;
	private Integer annualLeaveTime;
}
