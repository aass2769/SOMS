package project.soms.submission.repository;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;

import java.util.List;

public interface ApprovalListRepository {

  List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  void approvalOpen(Long submissionNo);

  ExpenseApprovalDetailForm expenseApprovalDetail(Long submissionNo, String submissionPri);

  List<ApproverDto> expenseApproverList(Long submissionNo, String submissionPri);

  void approve(Long submissionNO);

  void nextApproverShowable(String submissionPri, Long proposerEmployeeNo);

  void rejectApproval(Long submissionNo, String submissionComent);


}
