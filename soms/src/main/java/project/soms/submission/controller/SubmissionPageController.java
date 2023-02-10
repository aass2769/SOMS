package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.soms.common.dto.CommonDto;
import project.soms.common.service.CommonService;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ExpenseDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.ExpenseInsertForm;
import project.soms.submission.service.ApprovalSubmitService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/form")
public class SubmissionPageController {

  private final EmployeeService employeeService;
  private final ApprovalSubmitService approvalSubmitService;
  private final CommonService commonService;

  @GetMapping("expense")
  public String expenseForm(Model model, HttpServletRequest request) {

    /**
     * 페이지에 전달할 값을 담기 위한 model
     * 세션의 값을 가져오기 위한 request
     */
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    ProposerDto login_employee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 expenseInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.expenseApprover(login_employee.getEmployeeNo());
    model.addAttribute("approverDto", approverDto);
    model.addAttribute("expenseInsertForm", new ExpenseDto());

    return "submission/submitForm/expense";
  }

  @PostMapping("expense")
  public String submitv1(SubmissionDto submissionDto, @Valid ExpenseInsertForm expenseInsertForm, BindingResult result,
                         Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    /**
     * submissionDto 에는 결재 서식의 기본 정보 : 서식번호, 상신 일시가 전달됨
     * expenseInsertForm 에는 지출 구분, 지출일, 지출 내용, 소속 pjt, 힙계 금액이 전달됨
     * expenseResult 는 서식의 검증 결과를 만들기 위해 추가
     * employeeNo 는 기안자의 사번
     * request 는 서식 검증 실패시 서식 패이지로 반환할 때 세션값을 추가, 파라미터로 넘러온
     * model 은 서식 검증 실패시 서식 패이지로 반환할 때 서식 내용을 유지하기 위해 추가
     * redirect는 검증 완료 후 method=post 를 get으로 저장된 경로로 보내기 위해 추가
     */

    //검증된 값을 지출결의서 dto 클래스에 저장
    ExpenseDto expenseDtos = new ExpenseDto(expenseInsertForm.getExpenseSection(), expenseInsertForm.getExpensePjt(), expenseInsertForm.getExpenseDate(),
        expenseInsertForm.getExpenseCost(), expenseInsertForm.getExpenseContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getGlobalErrorCount());

      //들어온 값을 다시 보내기 위해 모델에 저장
      model.addAttribute("expenseInsertForm", expenseInsertForm);
      model.addAttribute("approverDto", approverDto);
      //페이지 내에 기본적으로 필요한 값들을 저장
      employeeInfo(model, request);
      normalList(model, request);
      //해당 페이지로 리턴
      return "submission/submitForm/expense";
    }


    //해당 서식을 저장하는 메서드 호출하여 insert 진행
    approvalSubmitService.expenseSubmit(submissionDto, expenseDtos, employeeNo, approverDto);

    //결재 내역 - 결재중 페이지로 이동
    return "redirect:/submission/form/underApproval";
  }

  private List<ApproverDto> approverCheck(BindingResult result, HttpServletRequest request, Long employeeNo) {
    //결재자 정보 배열 생성
    List<ApproverDto> approverDto = new ArrayList<>();

    //결재자 사번과 결재구분 배열에 값을 추가
    for (int i = 0; i < 8; i++) {
      if (request.getParameter("approverNo"+i) != null && request.getParameter("approverNo" + i) != ""){
        approverDto.add(new ApproverDto(
            Long.parseLong(request.getParameter("approverNo" + i)),
            request.getParameter("approverName" + i),
            request.getParameter("submissionSection" + i)));
      }
    }
    //결재자가 없으면 오류에 추가
    if (approverDto.size() == 0) {
      result.reject("approverIsNull", new Object[]{}, null);
    }
    //결재자 선택에 중복이 있으면 오류 추가
    boolean check = false;
    for (ApproverDto i : approverDto) {
      for (ApproverDto j : approverDto) {
        ProposerDto approverCheck1 = employeeService.proposer(i.getEmployeeNo());
        ProposerDto approverCheck2 = employeeService.proposer(j.getEmployeeNo());
        if (approverCheck1.getEmployeeNo().equals(approverCheck2.getEmployeeNo()) && i != j) {
          result.reject("approverIsDuplocation", new Object[]{}, null);
          check = true;
          break;
        }
      }

    }
    //결재자 관계 선택이 올바르지 않거나 결재라인에 본인이 포함되면 오류 추가
    for (int i = 1; i < approverDto.size(); i++) {
      ProposerDto approverCheck1 = employeeService.proposer(approverDto.get(i - 1).getEmployeeNo());
      ProposerDto approverCheck2 = employeeService.proposer(approverDto.get(i).getEmployeeNo());
      ProposerDto proposer = employeeService.proposer(employeeNo);
      if (proposer.getEmployeeNo().equals(approverCheck1.getEmployeeNo()) || proposer.getEmployeeNo().equals(approverCheck2.getEmployeeNo())) {
        result.reject("approverIsNotProposer", new Object[]{}, null);
      } else if (approverCheck1.getManageNo() > approverCheck2.getManageNo()) {
        result.reject("approverIsMiss", new Object[]{}, null);
        break;
      }
    }
    return approverDto;
  }

  private HttpSession employeeInfo(Model model, HttpServletRequest request) {
    //파라미터 하나로 합치기 가능 여부 확인
    ProposerDto employee = employeeService.proposer(20230201011L);
    HttpSession session = request.getSession();

    //세션에서 회원 정보를 조회
    session.setAttribute("LOGIN_EMPLOYEE", employee);
    ProposerDto login_employee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");
    //회원정보에 맞는 결재라인 자동 생성
    model.addAttribute("date", new Date());
    model.addAttribute("employee", login_employee);
    return session;
  }

  private void normalList(Model model, HttpServletRequest request) {

    String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};

    for (int i = 1; i <= teams.length; i++) {
      List<CommonDto> teamEmployeeList = commonService.commonList(teams[i - 1]);
      model.addAttribute("team" + i, teamEmployeeList);
    }
    List<CommonDto> executiveList = commonService.executiveList(teams);

    String employeeTeam = request.getParameter("employeeTeam");
    String manage = request.getParameter("manage");
    String employeeName = request.getParameter("employeeName");
    if (employeeTeam != null || manage != null || employeeName != null) {
      if(employeeTeam == null || Arrays.asList(teams).indexOf(employeeTeam) < 0) {
        employeeTeam = "";
      }
      if(manage == null) {
        manage = "";
      }
      List<CommonDto> selectList = commonService.commonSelect(employeeTeam, manage, employeeName);
      List<CommonDto> list = new ArrayList<>();
      if(Arrays.asList(teams).indexOf(employeeTeam) < 0 && request.getParameter("employeeTeam") != null) {
        for (CommonDto select : selectList) {
          if(Arrays.asList(teams).indexOf(select.getEmployeeTeam()) < 0) {
            list.add(select);
          }
        }
        selectList = list;
      }
      model.addAttribute("commonSelect", selectList);
    }
    model.addAttribute("team0", executiveList);
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
