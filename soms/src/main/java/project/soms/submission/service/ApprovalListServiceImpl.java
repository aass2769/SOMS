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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovalListServiceImpl implements ApprovalListService{

  private final ApprovalListRepository approvalListRepository;

  @Override
  public List<SubmissionDto> underApprovalList(Long employeeNo, HttpServletRequest request) {

    String approvalListSection = request.getParameter("approvalSection");
    //검색 조건이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    List<SubmissionDto> approvalList = new ArrayList<>();

    if (approvalListSection.equals("under")) {
      approvalList = approvalListRepository.underApprovalList(employeeNo, submissionSection, submissionDatetime);
    } else if (approvalListSection.equals("complete")) {
      approvalList = approvalListRepository.completeApprovalList(employeeNo, submissionSection, submissionDatetime);
    } else if (approvalListSection.equals("reject")) {
      approvalList = approvalListRepository.rejectedApprovalList(employeeNo, submissionSection, submissionDatetime);
    }
    return approvalList;
  }

  @Override
  public List<SubmissionDto> completeApprovalList(Long employeeNo, HttpServletRequest request) {

    //검색 조건이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    List<SubmissionDto> approvalList = approvalListRepository.completeApprovalList(employeeNo, submissionSection, submissionDatetime);
    return approvalList;
  }

  @Override
  public List<SubmissionDto> rejectedApprovalList(Long employeeNo, HttpServletRequest request) {

    //검색 조건이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    List<SubmissionDto> approvalList = approvalListRepository.rejectedApprovalList(employeeNo, submissionSection, submissionDatetime);
    return approvalList;
  }

  @Override
  public ExpenseApprovalDetailForm expenseApprovalDetail(HttpServletRequest request) {

    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    ExpenseApprovalDetailForm expenseApprovalDetailForm = approvalListRepository.expenseApprovalDetail(submissionNo, submissionPri, employeeNo);
    return expenseApprovalDetailForm;
  }

  @Override
  public List<ApproverDto> approverList(HttpServletRequest request) {

    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    List<ApproverDto> approverList = new ArrayList<>();
    String joinValue = request.getParameter("joinValue");
    if (joinValue.equals("expense")) {
      approverList = approvalListRepository.expenseApproverList(submissionNo, submissionPri);
    } else if (joinValue.equals("overtime")) {
      approverList = approvalListRepository.overtimeApproverList(submissionNo, submissionPri);
    } /*else if (joinValue.equals("annualLeave")) {

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
      log.error("결재자 있음={}", request.getParameter("nextApprover"));
    } else {
      approvalListRepository.comProposerOpen(Long.valueOf(request.getParameter("proposerValue")));
      log.error("결재자 없음={}", request.getParameter("proposerValue"));
    }
  }

  @Override
  public void rejectApproval(HttpServletRequest request) {
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionComent = request.getParameter("submissionComent");
    String submissionPri = request.getParameter("submissionPri");
    Long approverEmployeeNo = Long.valueOf(request.getParameter("approverEmployeeNo"));


    approvalListRepository.rejectApproval(submissionNo, submissionComent);
    approvalListRepository.comProposerOpen(Long.valueOf(request.getParameter("rejectValue")));
    if (request.getParameter("nextApprover") != null && !request.getParameter("nextApprover").equals("")) {
      approvalListRepository.rejectOpen(submissionPri, approverEmployeeNo);
    }
  }

  @Override
  public void deleteApproval(HttpServletRequest request) {
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));

    approvalListRepository.deleteApproval(submissionNo);
  }
}
