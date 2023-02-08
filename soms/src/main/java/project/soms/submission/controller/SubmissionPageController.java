package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ExpenseDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseInsertForm;
import project.soms.submission.dto.form.SubmissionInsertForm;
import project.soms.submission.service.ApprovalSubmitService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission")
public class SubmissionPageController {

  private final EmployeeService employeeService;
  private final ApprovalSubmitService approvalSubmitService;

  SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
  String datetime = datetimeFormat.format(new Date());
  String date = dateFormat.format(new Date());


  @GetMapping("form/expense")
  public String expenseForm(Model model, HttpServletRequest request) {

    employeeInfo(model, request);
    model.addAttribute("submissionInsertForm", new SubmissionInsertForm());
    model.addAttribute("expenseInsertForm", new ExpenseInsertForm());

    return "submission/submitForm/expense";
  }

  @PostMapping("form/expense")
  public String submitv1(@Validated SubmissionInsertForm submissionInsertForm, BindingResult submissionBindingresult,
                         @Validated ExpenseInsertForm expenseInsertForm, BindingResult expenseBindingresult,
                         Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    if (submissionBindingresult.hasErrors() || expenseBindingresult.hasErrors()) {
      log.info("errors={}", submissionBindingresult.getFieldErrors());
      log.info("errors={}", expenseBindingresult.getFieldErrors());
      model.addAttribute("submissionInsertForm", submissionInsertForm);
      model.addAttribute("expenseInsertForm", expenseInsertForm);
      employeeInfo(model, request);
      return "submission/submitForm/expense";
    }

    ExpenseDto expenseDtos = new ExpenseDto(expenseInsertForm.getExpenseSection(), expenseInsertForm.getExpenseDate(),
        expenseInsertForm.getExpenseCost(), expenseInsertForm.getExpenseContent());
    SubmissionDto submissionDtos = new SubmissionDto(
        submissionInsertForm.getSubmissionPri(), submissionInsertForm.getSubmissionDatetime(), submissionInsertForm.getSubmissionPjt());
    List<Long> approverDto = new ArrayList<>();
    List<String> submissionSection = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      if (request.getParameter("approver"+i) != null && request.getParameter("approver" + i) != ""){
        approverDto.add(Long.parseLong(request.getParameter("approver" + i)));
        submissionSection.add(request.getParameter("submissionSection" + i));
      }
    }

    approvalSubmitService.expenseSubmit(submissionDtos, expenseDtos, employeeNo, approverDto, submissionSection);
    return "submission/approvalList/underApproval";
  }

  private void employeeInfo(Model model, HttpServletRequest request) {
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
