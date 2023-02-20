package project.soms.submission.dto;

import lombok.Data;

@Data
//결재자 필드값
public class ApproverDto {

  private Long employeeNo;
  private String employeeName;
  private Long manageNo;
  private String manage;
  private String submissionSection;
  private String submissionStatus;

  public ApproverDto(Long employeeNo, String employeeName, String submissionSection, String submissionStatus) {
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
    this.submissionSection = submissionSection;
    this.submissionStatus = submissionStatus;
  }

  public ApproverDto(Long employeeNo, String employeeName, String submissionSection) {
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
    this.submissionSection = submissionSection;
  }

  public ApproverDto() {
  }

}
