package project.soms.submission.service;

import project.soms.submission.dto.*;

import java.util.List;

//결재 상신을 처리하는 service인터페이스
public interface ApprovalSubmitService {

  //지출결의서 상신
  void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  //연장근로신청서 상신
  void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  //연차신청서 상신
  void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  //출장신청서 상신
  void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  //시말서 상신
  void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

}
