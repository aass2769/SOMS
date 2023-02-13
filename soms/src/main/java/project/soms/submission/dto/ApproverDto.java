package project.soms.submission.dto;

import lombok.Data;

@Data
public class ApproverDto {

  private Long employeeNo;
  private String employeeName;
  private Long manageNo;
  private String manage;
  private String submissionSection;

  public ApproverDto(Long employeeNo, String employeeName, String submissionSection) {
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
    this.submissionSection = submissionSection;
  }

  public ApproverDto() {
  }
}
