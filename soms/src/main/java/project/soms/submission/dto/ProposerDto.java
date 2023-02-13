package project.soms.submission.dto;

import lombok.Data;

@Data
public class ProposerDto {

  private Long employeeNo;
  private String employeeName;
  private String employeeTeam;
  private Long manageNo;
  private String manage;

  public ProposerDto(Long employeeNo, String employeeName, String employeeTeam, Long manageNo, String manage) {
    this.employeeNo = employeeNo;
    this.employeeName = employeeName;
    this.employeeTeam = employeeTeam;
    this.manageNo = manageNo;
    this.manage = manage;
  }

  public ProposerDto() {
  }
}
