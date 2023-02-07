package project.soms.submission.dto;

import lombok.Data;

@Data
public class SubmissionDto {

  private Long submissionNo;
  private String submissionPri;
  private String submissionDatetime;
  private String submissionPjt;
  private String submissionSection;
  private String submissionStatus;
  private String submissionComent;
  private String submissionOpen;
  private String submissionShowable;
  private Long proposerEmployeeNo;
  private Long approverEmployeeNo;
  private Long expenseNo;
  private Long overtimeNo;
  private Long annualLeaveNo;
  private Long businessTripNo;
  private Long incidentNo;

  public SubmissionDto() {
  }


  public SubmissionDto(String submissionPri, String submissionDatetime, String submissionPjt) {
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
    this.submissionPjt = submissionPjt;
  }

  public SubmissionDto(String submissionPri, String submissionDatetime, String submissionStatus, String submissionPjt, String submissionOpen) {
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
    this.submissionStatus = submissionStatus;
    this.submissionPjt = submissionPjt;
    this.submissionOpen = submissionOpen;
  }

  public SubmissionDto(String submissionPri, String submissionDatetime, String submissionPjt, String submissionSection, String submissionStatus, String submissionComent, String submissionOpen, String submissionShowable, Long proposerEmployeeNo, Long approverEmployeeNo, Long expenseNo, Long overtimeNo, Long annualLeaveNo, Long businessTripNo, long incidentNo) {
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
    this.submissionPjt = submissionPjt;
    this.submissionSection = submissionSection;
    this.submissionStatus = submissionStatus;
    this.submissionComent = submissionComent;
    this.submissionOpen = submissionOpen;
    this.submissionShowable = submissionShowable;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.expenseNo = expenseNo;
    this.overtimeNo = overtimeNo;
    this.annualLeaveNo = annualLeaveNo;
    this.businessTripNo = businessTripNo;
    this.incidentNo = incidentNo;
  }

  public SubmissionDto(Long submissionNo, String submissionPri, String submissionDatetime, String submissionPjt, String submissionSection, String submissionStatus, String submissionComent, String submissionOpen, String submissionShowable, Long proposerEmployeeNo, Long approverEmployeeNo, Long expenseNo, Long overtimeNo, Long annualLeaveNo, Long businessTripNo, Long incidentNo) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
    this.submissionPjt = submissionPjt;
    this.submissionSection = submissionSection;
    this.submissionStatus = submissionStatus;
    this.submissionComent = submissionComent;
    this.submissionOpen = submissionOpen;
    this.submissionShowable = submissionShowable;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.expenseNo = expenseNo;
    this.overtimeNo = overtimeNo;
    this.annualLeaveNo = annualLeaveNo;
    this.businessTripNo = businessTripNo;
    this.incidentNo = incidentNo;
  }
}
