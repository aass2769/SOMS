package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.service.ApprovalListService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("submission/approval")
public class ApprovalController {

  private final ApprovalListService approvalListService;
  private final EmployeeService employeeService;

  @GetMapping("underApprovalList")
  public String underApprovalList(HttpServletRequest request, Model model) {

    //세션에서 사원의 정보를 조회
    HttpSession session = setTestSession(request);
    ProposerDto login_employee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");

    //결재중 내역을 반화히여 모델에 저장
    List<SubmissionDto> approvalList = approvalListService.underApprovalList(login_employee.getEmployeeNo(), request);
    model.addAttribute("approvalList", approvalList);

    return "submission/approvalList/underApproval";
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
