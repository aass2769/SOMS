package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;
import project.soms.submission.repository.ApprovalListRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalListServiceImpl implements ApprovalListService{

  private final ApprovalListRepository approvalListRepository;

  @Override
  public List<SubmissionDto> underApprovalList(Long employeeNo, HttpServletRequest request) {

    //검색 조건이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    List<SubmissionDto> approvalList = approvalListRepository.underApprovalList(employeeNo, submissionSection, submissionDatetime);
    return approvalList;
  }

  @Override
  public void approvalOpen(HttpServletRequest request) {
    if (request.getParameter("submissionOpen").equals("미열람")) {

      approvalListRepository.approvalOpen(Long.valueOf(request.getParameter("submissionNo")));

    }
  }

  @Override
  public ExpenseApprovalDetailForm expenseApprovalDetail(HttpServletRequest request) {

    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");

    if (submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    ExpenseApprovalDetailForm expenseApprovalDetailForm = approvalListRepository.expenseApprovalDetail(submissionNo, submissionPri);
    return expenseApprovalDetailForm;
  }

  @Override
  public List<ApproverDto> approverList(HttpServletRequest request) {

    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    List<ApproverDto> approverList = approvalListRepository.expenseApproverList(submissionNo, submissionPri);
    /*String joinValue = request.getParameter("joinValue");
    if (joinValue.equals("expense")) {

    } else if (joinValue.equals("overtime")) {

    } else if (joinValue.equals("annualLeave")) {

    } else if (joinValue.equals("businessTrip")) {

    } else if (joinValue.equals("incident")) {

    }*/
    return approverList;
  }

  @Transactional
  @Override
  public void approve(HttpServletRequest request) {
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    Long approverEmployeeNo = Long.valueOf(request.getParameter("approverEmployeeNo"));

    approvalListRepository.approve(submissionNo);
    if (request.getParameter("nextApprover") != null && !request.getParameter("nextApprover").equals("")) {
      approvalListRepository.nextApproverShowable(submissionPri, approverEmployeeNo);
    }
  }

  @Override
  public void rejectApproval(HttpServletRequest request) {
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionComent = request.getParameter("submissionComent");

    approvalListRepository.rejectApproval(submissionNo, submissionComent);
  }
}
