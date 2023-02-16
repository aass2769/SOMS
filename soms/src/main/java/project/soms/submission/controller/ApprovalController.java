package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseApprovalDetailForm;
import project.soms.submission.service.ApprovalListService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/approval")
public class ApprovalController {

  private final ApprovalListService approvalListService;
  private final EmployeeService employeeService;

  @GetMapping("approvalList")
  public String underApprovalList(HttpServletRequest request, Model model) {

    //세션에서 사원의 정보를 조회
    HttpSession session = setTestSession(request);
    ProposerDto login_employee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");

    String responsePage = request.getParameter("approvalSection");

    //결재중 내역을 반화히여 모델에 저장
    List<SubmissionDto> approvalList = approvalListService.underApprovalList(login_employee.getEmployeeNo(), request);
    model.addAttribute("employeeNo", login_employee.getEmployeeNo());
    model.addAttribute("approvalList", approvalList);

    if (responsePage.equals("under")) {
      return "submission/approvalList/underApproval";
    } else if (responsePage.equals("complete")) {
      return "submission/approvalList/completeApproval";
    } else if (responsePage.equals("reject")) {
      return "submission/approvalList/rejectedApproval";
    }

    return "redirect:"+ request.getRequestURL();
  }

  @PostMapping("detail/expense")
  public String approvalDetailExpense(HttpServletRequest request, Model model) {

    ExpenseApprovalDetailForm expenseApprovalDetail = approvalListService.expenseApprovalDetail(request);
    expenseApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    ProposerDto proposer = employeeService.proposer(expenseApprovalDetail.getProposerEmployeeNo());
    List<ApproverDto> approverList = approvalListService.approverList(request);

    model.addAttribute("nextApproverCheck", request.getParameter("nextApproverCheck"));
    log.error("nextApproverCheck={}", request.getParameter("nextApproverCheck"));
    model.addAttribute("employeeNo", request.getParameter("employeeNo"));
    model.addAttribute("approvalSection", request.getParameter("approvalSection"));
    model.addAttribute("expenseApprovalDetail", expenseApprovalDetail);
    model.addAttribute("proposer", proposer);
    model.addAttribute("approverList", approverList);

    return "submission/approvalForm/expense";
  }

  @PostMapping("approve")
  public String approve(HttpServletRequest request) {
    approvalListService.approve(request);
    if (request.getParameter("nextApprover") != null && !request.getParameter("nextApprover").equals("")) {
      return "redirect:/submission/approval/underApprovalList";
    }
    return "redirect:/submission/approval/completeApproval";
  }

  @PostMapping("reject")
  public String reject(HttpServletRequest request) {

    approvalListService.rejectApproval(request);

    return "redirect:/submission/approvalList/rejectedApproval";
  }

  @PostMapping("delete")
  public String delete(HttpServletRequest request) {

    approvalListService.deleteApproval(request);

    return "redirect:" + request.getHeader("Referer");
  }

  private HttpSession setTestSession(HttpServletRequest request) {
    //파라미터 하나로 합치기 가능 여부 확인
    ProposerDto employee = employeeService.proposer(20230201011L);
    HttpSession session = request.getSession();

    //세션에서 회원 정보를 조회
    session.setAttribute("LOGIN_EMPLOYEE", employee);
    return session;
  }


}
