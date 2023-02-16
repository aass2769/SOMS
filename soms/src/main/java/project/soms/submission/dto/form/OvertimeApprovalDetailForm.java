package project.soms.submission.dto.form;

import lombok.Data;

@Data
public class OvertimeApprovalDetailForm {

  private Long submissionNo;
  private String submissionPri;
  private String submissionSection;
  private String submissionStatus;
  private String approvalAble;
  private Long proposerEmployeeNo;
  private Long approverEmployeeNo;
  private Long overtimeNo;
  private String overtimeSection;
  private String overtimePjt;
  private String overtimeStartDate;
  private Integer overtimeStartTime;
  private String overtimeEndDate;
  private Integer overtimeEndTime;
  private String overtimeContent;

  public OvertimeApprovalDetailForm(Long submissionNo, String submissionPri, String approvalAble, Long proposerEmployeeNo, Long approverEmployeeNo, Long overtimeNo, String overtimeSection, String overtimePjt, String overtimeStartDate, Integer overtimeStartTime, String overtimeEndDate, Integer overtimeEndTime, String overtimeContent) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.approvalAble = approvalAble;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.overtimeNo = overtimeNo;
    this.overtimeSection = overtimeSection;
    this.overtimePjt = overtimePjt;
    this.overtimeStartDate = overtimeStartDate;
    this.overtimeStartTime = overtimeStartTime;
    this.overtimeEndDate = overtimeEndDate;
    this.overtimeEndTime = overtimeEndTime;
    this.overtimeContent = overtimeContent;
  }

  public OvertimeApprovalDetailForm() {
  }
}
