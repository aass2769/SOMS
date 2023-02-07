package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.service.ApprovalSubmitService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/form")
public class SubmissionPageController {

  private final EmployeeService employeeService;
  private final ApprovalSubmitService approvalSubmitService;

  SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
  String datetime = datetimeFormat.format(new Date());
  String date = dateFormat.format(new Date());


  @GetMapping("expense")
  public String expenseForm(Model model, HttpServletRequest request) {

    ProposerDto employee = employeeService.proposer(20230201011L);
    HttpSession session = request.getSession();

    //세션에서 회원 정보를 조회
    session.setAttribute("LOGIN_EMPLOYEE", employee);
    ProposerDto login_employee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");

    //회원정보에 맞는 결재라인 자동 생성
    List<ApproverDto> approvers = employeeService.expenseApprover(login_employee.getEmployeeNo());
    log.info("class={}", approvers.get(0));

    //모델에 결재라인과 기안자 정보 저장
    model.addAttribute("date", date);
    model.addAttribute("datetime", datetime);
    model.addAttribute("employee", login_employee);
    model.addAttribute("approvers", approvers);

    return "submission/submitForm/expense";
  }

  @GetMapping("overtime")
  public String overtimeForm() {
    return "submission/submitForm/overtime";
  }

  @GetMapping("annualLeave")
  public String annualLeaveForm() {
    return "submission/submitForm/annualLeave";
  }

  @GetMapping("businessTrip")
  public String businessTripForm() {
    return "submission/submitForm/businessTrip";
  }

  @GetMapping("incident")
  public String incidentForm() {
    return "submission/submitForm/incident";
  }

  @GetMapping("underApproval")
  public String underApproval() {
    return "submission/approvalList/underApproval";
  }

  @GetMapping("completeApproval")
  public String completeApproval() {
    return "submission/approvalList/completeApproval";
  }

  @GetMapping("rejectedApproval")
  public String rejectedApproval() {
    return "submission/approvalList/rejectedApproval";
  }

}
