package project.soms.submission.dto;

import lombok.Data;

@Data
public class ApproverDto {

  private Long employeeNo;
  private String employeeName;
  private Long manageNo;
  private String manage;

  public ApproverDto(Long employeeNo) {
    this.employeeNo = employeeNo;
  }
}
