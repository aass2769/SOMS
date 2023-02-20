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
import project.soms.employee.dto.EmployeeDto;
import project.soms.submission.dto.*;
import project.soms.submission.dto.form.*;
import project.soms.submission.service.ApprovalSubmitService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/form")
//기안, 결재자 조회 및 결재 작성, 상신을 담당하는 컨트롤러
public class SubmissionSubmitController {

  /**
   * 기안자, 결재자의 정보를 조회하는 service,
   * 결재정보를 insert하는 service
   * 결재자 변경을 위한 임직원 리스트 조회 service
   */
  private final EmployeeService employeeService;
  private final ApprovalSubmitService approvalSubmitService;
  private final CommonService commonService;

  //지출결의서 화면 응답
  @GetMapping("expense")
  public String expenseForm(Model model, HttpServletRequest request) {

    /**
     * 페이지에 전달할 값을 담기 위한 model
     * 세션의 값을 가져오기 위한 request
     */
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 expenseInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.expenseApprover(login_employee.getEmployeeNo());

    model.addAttribute("approverDto", approverDto);
    model.addAttribute("expenseInsertForm", new ExpenseDto());

    return "submission/submitForm/expense";
  }

  //지출결의서 상신 처리
  @PostMapping("expense")
  public String expenseSubmit(SubmissionDto submissionDto, @Valid ExpenseInsertForm expenseInsertForm, BindingResult result,
                              Long employeeNo, HttpServletRequest request, Model model) {

    /**
     * submissionDto 에는 결재 서식의 기본 정보 : 서식번호, 상신 일시가 전달됨
     * expenseInsertForm 에는 지출 구분, 지출일, 지출 내용, 소속 pjt, 힙계 금액이 전달됨
     * result 는 서식의 검증 결과를 만들기 위해 추가
     * employeeNo 는 기안자의 사번
     * request 는 서식 검증 실패시 서식 패이지로 반환할 때 세션값을 추가, 파라미터로 넘러온
     * model 은 서식 검증 실패시 서식 패이지로 반환할 때 서식 내용을 유지하기 위해 추가
     * redirect는 검증 완료 후 method=post 를 get으로 저장된 경로로 보내기 위해 추가
     */

    //결재자 선택을 위한 조회 클릭시 선택된 임직원 리스트 포함해서 응답
    if (request.getParameter("check") != null) {
      model.addAttribute("expenseInsertForm", new ExpenseDto(expenseInsertForm.getExpenseSection(),
          expenseInsertForm.getExpensePjt(), expenseInsertForm.getExpenseDate(), expenseInsertForm.getExpenseCost(),
          expenseInsertForm.getExpenseContent()));
      selectCommonList(request, model);
      return "submission/submitForm/expense";
    }

    //검증된 값을 지출결의서 dto 클래스에 저장
    ExpenseDto expenseDtos = new ExpenseDto(expenseInsertForm.getExpenseSection(), expenseInsertForm.getExpensePjt(), expenseInsertForm.getExpenseDate(),
        expenseInsertForm.getExpenseCost(), expenseInsertForm.getExpenseContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = employeeService.approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getAllErrors());

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
    return "redirect:/submission/approval/approvalList?approvalSection=under";
  }

  //연장근로신청서 화면 응답
  @GetMapping("overtime")
  public String overtimeForm(Model model, HttpServletRequest request) {

    //세션 정보 조회 및 리스트, 기안자 정보 생성
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 overtimeInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.overtimeApprover(login_employee.getEmployeeNo());

    model.addAttribute("approverDto", approverDto);
    model.addAttribute("overtimeInsertForm", new OvertimeDto());

    return "submission/submitForm/overtime";
  }

  //연장근로 상신 처리
  @PostMapping("overtime")
  public String overtimeSubmit(SubmissionDto submissionDto, @Valid OvertimeInsertForm overtimeInsertForm, BindingResult result,
                               Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    //결재자 선택을 위한 조회 클릭시 선택된 임직원 리스트 포함해서 응답
    if (request.getParameter("check") != null) {
      model.addAttribute("overtimeInsertForm", new OvertimeDto(overtimeInsertForm.getOvertimeSection(),
          overtimeInsertForm.getOvertimePjt(), overtimeInsertForm.getOvertimeStartDate(), overtimeInsertForm.getOvertimeStartTime(),
          overtimeInsertForm.getOvertimeEndDate(), overtimeInsertForm.getOvertimeEndTime(), overtimeInsertForm.getOvertimeContent()));
      selectCommonList(request, model);
      return "submission/submitForm/overtime";
    }

    //검증된 값을 연장근로 dto 클래스에 저장
    OvertimeDto OvertimeDtos = new OvertimeDto(overtimeInsertForm.getOvertimeSection(),
        overtimeInsertForm.getOvertimePjt(), overtimeInsertForm.getOvertimeStartDate(), overtimeInsertForm.getOvertimeStartTime(),
        overtimeInsertForm.getOvertimeEndDate(), overtimeInsertForm.getOvertimeEndTime(), overtimeInsertForm.getOvertimeContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = employeeService.approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getAllErrors());

      //들어온 값을 다시 보내기 위해 모델에 저장
      model.addAttribute("overtimeInsertForm", overtimeInsertForm);
      model.addAttribute("approverDto", approverDto);
      //페이지 내에 기본적으로 필요한 값들을 저장
      employeeInfo(model, request);
      normalList(model, request);
      //해당 페이지로 리턴
      return "submission/submitForm/overtime";
    }

    //해당 서식을 저장하는 메서드 호출하여 insert 진행
    approvalSubmitService.overtimeSubmit(submissionDto, OvertimeDtos, employeeNo, approverDto);

    //결재 내역 - 결재중 페이지로 이동
    return "redirect:/submission/approval/approvalList?approvalSection=under";
  }

  //연차신청서 화면 응답
  @GetMapping("annualLeave")
  public String annualLeaveForm(Model model, HttpServletRequest request) {

    //세션 정보 조회 및 리스트, 기안자 정보 생성
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 annualLeaveInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.annualLeaveApprover(login_employee.getEmployeeNo());

    model.addAttribute("approverDto", approverDto);
    model.addAttribute("annualLeaveInsertForm", new AnnualLeaveDto());

    return "submission/submitForm/annualLeave";
  }

  //연차신청서 상신 처리
  @PostMapping("annualLeave")
  public String annualLeaveSubmit(SubmissionDto submissionDto, @Valid AnnualLeaveInsertForm annualLeaveInsertForm, BindingResult result,
                                Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    //결재자 선택을 위한 조회 클릭시 선택된 임직원 리스트 포함해서 응답
    if (request.getParameter("check") != null) {
      model.addAttribute("annualLeaveInsertForm", new AnnualLeaveDto(annualLeaveInsertForm.getAnnualLeaveSection(),
          annualLeaveInsertForm.getAnnualLeavePjt(), annualLeaveInsertForm.getAnnualLeaveStart(), annualLeaveInsertForm.getAnnualLeaveEnd(),
          annualLeaveInsertForm.getAnnualLeaveTime(), annualLeaveInsertForm.getAnnualLeaveContent()));
      selectCommonList(request, model);
      return "submission/submitForm/annualLeave";
    }

    //검증된 값을 지출결의서 dto 클래스에 저장
    AnnualLeaveDto AnnualLeaveDtos = new AnnualLeaveDto(annualLeaveInsertForm.getAnnualLeaveSection(), annualLeaveInsertForm.getAnnualLeavePjt(),
        annualLeaveInsertForm.getAnnualLeaveStart(), annualLeaveInsertForm.getAnnualLeaveEnd(),
        annualLeaveInsertForm.getAnnualLeaveTime(), annualLeaveInsertForm.getAnnualLeaveContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = employeeService.approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getAllErrors());

      //들어온 값을 다시 보내기 위해 모델에 저장
      model.addAttribute("annualLeaveInsertForm", annualLeaveInsertForm);
      model.addAttribute("approverDto", approverDto);
      //페이지 내에 기본적으로 필요한 값들을 저장
      employeeInfo(model, request);
      normalList(model, request);
      //해당 페이지로 리턴
      return "submission/submitForm/annualLeave";
    }

    //해당 서식을 저장하는 메서드 호출하여 insert 진행
    approvalSubmitService.annualLeaveSubmit(submissionDto, AnnualLeaveDtos, employeeNo, approverDto);

    //결재 내역 - 결재중 페이지로 이동
    return "redirect:/submission/approval/approvalList?approvalSection=under";
  }

  //출장신청서 화면 응답
  @GetMapping("businessTrip")
  public String businessTripForm(Model model, HttpServletRequest request) {

    //세션 정보 조회 및 리스트, 기안자 정보 생성
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 businessTripInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.businessTripApprover(login_employee.getEmployeeNo());

    model.addAttribute("approverDto", approverDto);
    model.addAttribute("businessTripInsertForm", new BusinessTripDto());

    return "submission/submitForm/businessTrip";
  }

  //출장신청서 상신 처리
  @PostMapping("businessTrip")
  public String businessTripSubmit(SubmissionDto submissionDto, @Valid BusinessTripInsertForm businessTripInsertForm, BindingResult result,
                                Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    //결재자 선택을 위한 조회 클릭시 선택된 임직원 리스트 포함해서 응답
    if (request.getParameter("check") != null) {
      model.addAttribute("businessTripInsertForm", new BusinessTripDto(businessTripInsertForm.getBusinessTripSection(),
          businessTripInsertForm.getBusinessTripPjt(), businessTripInsertForm.getBusinessTripStart(), businessTripInsertForm.getBusinessTripEnd(),
          businessTripInsertForm.getBusinessTripTime(), businessTripInsertForm.getBusinessTripDestination(), businessTripInsertForm.getBusinessTripContent()));
      selectCommonList(request, model);
      return "submission/submitForm/businessTrip";
    }

    //검증된 값을 지출결의서 dto 클래스에 저장
    BusinessTripDto businessTripDto = new BusinessTripDto(businessTripInsertForm.getBusinessTripSection(),
        businessTripInsertForm.getBusinessTripPjt(), businessTripInsertForm.getBusinessTripStart(), businessTripInsertForm.getBusinessTripEnd(),
        businessTripInsertForm.getBusinessTripTime(), businessTripInsertForm.getBusinessTripDestination(), businessTripInsertForm.getBusinessTripContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = employeeService.approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getAllErrors());

      //들어온 값을 다시 보내기 위해 모델에 저장
      model.addAttribute("businessTripInsertForm", businessTripInsertForm);
      model.addAttribute("approverDto", approverDto);
      //페이지 내에 기본적으로 필요한 값들을 저장
      employeeInfo(model, request);
      normalList(model, request);
      //해당 페이지로 리턴
      return "submission/submitForm/businessTrip";
    }

    //해당 서식을 저장하는 메서드 호출하여 insert 진행
    approvalSubmitService.businessTripSubmit(submissionDto, businessTripDto, employeeNo, approverDto);

    //결재 내역 - 결재중 페이지로 이동
    return "redirect:/submission/approval/approvalList?approvalSection=under";
  }

  //시말서 화면 응답
  @GetMapping("incident")
  public String incidentForm(Model model, HttpServletRequest request) {

    //세션 정보 조회 및 리스트, 기안자 정보 생성
    HttpSession session = employeeInfo(model, request);
    normalList(model, request);
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

    //해당 서식의 필드값 생성을 위한 incidentInsertForm 객체와 approver 배열 저장
    List<ApproverDto> approverDto = employeeService.incidentApprover(login_employee.getEmployeeNo());

    model.addAttribute("approverDto", approverDto);
    model.addAttribute("incidentInsertForm", new IncidentDto());

    return "submission/submitForm/incident";
  }

  //시말서 처리
  @PostMapping("incident")
  public String incidentSubmit(SubmissionDto submissionDto, @Valid IncidentInsertForm incidentInsertForm, BindingResult result,
                               Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {

    //결재자 선택을 위한 조회 클릭시 선택된 임직원 리스트 포함해서 응답
    if (request.getParameter("check") != null) {
      model.addAttribute("incidentInsertForm", new IncidentDto(incidentInsertForm.getIncidentSection(),
          incidentInsertForm.getIncidentPjt(), incidentInsertForm.getIncidentContent()));
      selectCommonList(request, model);
      return "submission/submitForm/incident";
    }

    //검증된 값을 지출결의서 dto 클래스에 저장
    IncidentDto incidentDto = new IncidentDto(incidentInsertForm.getIncidentSection(), incidentInsertForm.getIncidentPjt(),
        incidentInsertForm.getIncidentContent());

    //결재자 관계및 결재 라인 검증
    List<ApproverDto> approverDto = employeeService.approverCheck(result, request, employeeNo);

    //들어온 Dto 값들에 오류가 없는지 검증
    if (result.hasErrors()) {
      //오류 내역 로그
      log.warn("getFieldErrors={}", result.getFieldErrors());
      log.warn("getGlobalError={}", result.getAllErrors());

      //들어온 값을 다시 보내기 위해 모델에 저장
      model.addAttribute("incidentInsertForm", incidentInsertForm);
      model.addAttribute("approverDto", approverDto);
      //페이지 내에 기본적으로 필요한 값들을 저장
      employeeInfo(model, request);
      normalList(model, request);
      //해당 페이지로 리턴
      return "submission/submitForm/incident";
    }

    //해당 서식을 저장하는 메서드 호출하여 insert 진행
    approvalSubmitService.incidentSubmit(submissionDto, incidentDto, employeeNo, approverDto);

    //결재 내역 - 결재중 페이지로 이동
    return "redirect:/submission/approval/approvalList?approvalSection=under";
  }

  /**
   * 결재자 선택을 위한 임직원 조회시 조건을 이용한 조회에 사용될 메서드
   * 서식을 작성하는 페이지 5곳에서 사용됨
   */
  private void selectCommonList(HttpServletRequest request, Model model) {
    model.addAttribute("approverDto", getParameterApprovers(request));
    employeeInfo(model, request);
    normalList(model, request);
    model.addAttribute("selectCommons", "open");
    model.addAttribute("insertKey", request.getParameter("insertKey"));
  }

  /**
   * 기안자의 정보를 담는 메서드
   * 서식 안에 기안자의 정보를 자동완성시키기 위해 사용됨
   * 서식을 작성하는 페이지 5곳에서 사용됨
   */
  public HttpSession employeeInfo(Model model, HttpServletRequest request) {
    HttpSession session = request.getSession();
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");
    ProposerDto proposer = employeeService.proposer(login_employee.getEmployeeNo());
    //회원정보에 맞는 결재라인 자동 생성
    model.addAttribute("employee", proposer);
    model.addAttribute("date", new Date());
    return session;
  }

  /**
   * 결재자 선택을 위한 임직원 리스트를 담는 메서드
   * 서식을 작성하는 페이지 5곳에서 사용됨
   */
  public void normalList(Model model, HttpServletRequest request) {
    String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};
    String employeeTeam = request.getParameter("employeeTeam");
    String manage = request.getParameter("manage");
    String employeeName = request.getParameter("employeeName");

    // 검색 조건 확인 및 검색 리스트 생성
    if(employeeTeam != null || manage != null || employeeName != "") {
      List<CommonDto> selectList = commonService.selectedCommon(teams, employeeTeam, manage, employeeName);
      model.addAttribute("commonSelect", selectList);
    }

    //각 팀과 팀에 속한 인원의 직급, 이름, 사번을 가지고옴.
    for (int i = 1; i <= teams.length; i++) {
      List<CommonDto> teamEmployeeList = commonService.commonList(teams[i-1]);
      model.addAttribute("team" + i, teamEmployeeList);
    }

    //임원들의 리스트를 가지고옴. 공통, 경영 지원, 개발 연구, 영업 임원들이 있음 (사장, 부사장 포함).
    List<CommonDto> executiveList = commonService.executiveList(teams);
    model.addAttribute("team0", executiveList);

  }


  public static List<ApproverDto> getParameterApprovers(HttpServletRequest request) {
    List<ApproverDto> approverDto = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      if (request.getParameter("approverNo"+i) != null && request.getParameter("approverNo" + i) != ""){
        approverDto.add(new ApproverDto(
            Long.parseLong(request.getParameter("approverNo" + i)),
            request.getParameter("approverName" + i),
            request.getParameter("submissionSection" + i)));
      }
    }
    return approverDto;
  }
}
