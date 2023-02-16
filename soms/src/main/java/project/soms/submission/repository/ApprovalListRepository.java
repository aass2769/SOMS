package project.soms.submission.repository;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;
import project.soms.submission.dto.form.OvertimeApprovalDetailForm;

import java.util.List;

public interface ApprovalListRepository {

  List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  List<SubmissionDto> completeApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  List<SubmissionDto> rejectedApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  void approvalOpen(Long submissionNo);

  void rejectOpen(String submissionPri, Long proposerEmployeeNo);

  void comProposerOpen(Long submissionNo);

  ExpenseApprovalDetailForm expenseApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  List<ApproverDto> expenseApproverList(Long submissionNo, String submissionPri);

  OvertimeApprovalDetailForm overtimeApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  List<ApproverDto> overtimeApproverList(Long submissionNo, String submissionPri);

  void approve(Long submissionNO);

  void nextApproverShowable(String submissionPri, Long proposerEmployeeNo);

  void rejectApproval(Long submissionNo, String submissionComent);

  void deleteApproval(Long submissionNo);


}
