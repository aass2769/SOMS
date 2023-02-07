package project.soms.submission.service;

import project.soms.submission.dto.*;

import java.util.List;

public interface ApprovalSubmitService {

  void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo,
                     List<Long> approverDto, List<String> submissionSection);

  void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo,
                      List<Long> approverDto, List<String> submissionSection);

  void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo,
                         List<Long> approverDto, List<String> submissionSection);

  void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo,
                          List<Long> approverDto, List<String> submissionSection);

  void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo,
                      List<Long> approverDto, List<String> submissionSection);

}
