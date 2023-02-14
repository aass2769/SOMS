package project.soms.submission.dto;

import lombok.Data;

@Data
public class SubmissionDto {

  private Long submissionNo;
  private String submissionSubject;
  private String submissionPri;
  private String submissionDatetime;
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

  public SubmissionDto(String submissionPri, String submissionDatetime, String submissionStatus, String submissionOpen) {
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
    this.submissionStatus = submissionStatus;
    this.submissionOpen = submissionOpen;
  }

  public SubmissionDto(String submissionSubject, String submissionPri, String submissionDatetime, String submissionSection, String submissionStatus, String submissionComent, String submissionOpen, String submissionShowable, Long proposerEmployeeNo, Long approverEmployeeNo, Long expenseNo, Long overtimeNo, Long annualLeaveNo, Long businessTripNo, Long incidentNo) {
    this.submissionSubject = submissionSubject;
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
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

  public SubmissionDto(Long submissionNo, String submissionSubject, String submissionPri, String submissionDatetime, String submissionSection, String submissionStatus, String submissionComent, String submissionOpen, String submissionShowable, Long proposerEmployeeNo, Long approverEmployeeNo, Long expenseNo, Long overtimeNo, Long annualLeaveNo, Long businessTripNo, Long incidentNo) {
    this.submissionNo = submissionNo;
    this.submissionSubject = submissionSubject;
    this.submissionPri = submissionPri;
    this.submissionDatetime = submissionDatetime;
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
