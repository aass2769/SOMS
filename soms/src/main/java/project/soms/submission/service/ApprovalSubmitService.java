package project.soms.submission.service;

import project.soms.submission.dto.*;

import java.util.List;

public interface ApprovalSubmitService {

  void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

  void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo, List<ApproverDto> approverDto);

}
