package project.soms.submission.service;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ApprovalListService {

  List<SubmissionDto> underApprovalList(Long employeeNo, HttpServletRequest request);

  void approvalOpen(HttpServletRequest request);

  ExpenseApprovalDetailForm expenseApprovalDetail(HttpServletRequest request);

  List<ApproverDto> approverList(HttpServletRequest request);

  void approve(HttpServletRequest request);

  void rejectApproval(HttpServletRequest request);
}
