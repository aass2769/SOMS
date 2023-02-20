package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.employee.dto.EmployeeDto;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;
import project.soms.submission.service.ApprovalListService;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/approval")
//결재 내역 및 결재자 상신, 반려, 삭제 등을 담당하는 컨트롤러
public class ApprovalController {

  /**
   * 결재내역을 조회하는 service객체와 기안 결재자 생성을 위한 service객체 생성
   */
  private final ApprovalListService approvalListService;
  private final EmployeeService employeeService;

  //결재 내역 조회
  @GetMapping("approvalList")
  public String approvalList(HttpServletRequest request, Model model) {

    //세션에서 사원의 정보를 조회
    HttpSession session = request.getSession();
    EmployeeDto login_employee = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");


    //결재중 내역을 반환. 서비스 로직에서 결재중, 완료, 반려 내역을 구분하여 리스트에 저장
    List<SubmissionDto> approvalList = approvalListService.approvalList(login_employee.getEmployeeNo(), request);
    //결재자 사번과 결재 내역을 모델에 저장
    model.addAttribute("employeeNo", login_employee.getEmployeeNo());
    model.addAttribute("approvalList", approvalList);

    //응답할 페이지를 구분. '결재중 / 결재 완료 / 결재 반려' 를 확인하여 각자 페이지에 맞게 응답
    String responsePage = request.getParameter("approvalSection");
    if (responsePage.equals("under")) {
      return "submission/approvalList/underApproval";
    } else if (responsePage.equals("complete")) {
      return "submission/approvalList/completeApproval";
    } else if (responsePage.equals("reject")) {
      return "submission/approvalList/rejectedApproval";
    }
    return null;
  }

  //지출서식 상세 조회
  @PostMapping("detail/expense")
  public String approvalDetailExpense(HttpServletRequest request, Model model) {
    //결재 서식 상세 조회 후 모델에 저장
    ExpenseApprovalDetailForm expenseApprovalDetail = approvalListService.expenseApprovalDetail(request);
    expenseApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    model.addAttribute("expenseApprovalDetail", expenseApprovalDetail);

    //기안자 정보 조회 및 모델에 저장
    ProposerDto proposer = employeeService.proposer(expenseApprovalDetail.getProposerEmployeeNo());
    model.addAttribute("proposer", proposer);

    //사용자 및 결재자 정보 조회 및 저장
    proposerAndApproverInfo(request, model);

    return "submission/approvalForm/expense";
  }

  //연장근로서식 상세 조회
  @PostMapping("detail/overtime")
  public String approvalDetailOvertime(HttpServletRequest request, Model model) {
    //결재 서식 상세 조회 후 모델에 저장
    OvertimeApprovalDetailForm overtimeApprovalDetail = approvalListService.overtimeApprovalDetail(request);
    overtimeApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    model.addAttribute("overtimeApprovalDetail", overtimeApprovalDetail);

    //기안자 정보 조회 및 모델에 저장
    ProposerDto proposer = employeeService.proposer(overtimeApprovalDetail.getProposerEmployeeNo());
    model.addAttribute("proposer", proposer);

    //사용자 및 결재자 정보 조회 및 저장
    proposerAndApproverInfo(request, model);

    return "submission/approvalForm/overtime";
  }

  //연차서식 상세 조회
  @PostMapping("detail/annualLeave")
  public String approvalDetailAnnualLeave(HttpServletRequest request, Model model) {
    //결재 서식 상세 조회 후 모델에 저장
    AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail = approvalListService.annualLeaveApprovalDetail(request);
    annualLeaveApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    model.addAttribute("annualLeaveApprovalDetail", annualLeaveApprovalDetail);

    //기안자 정보 조회 및 모델에 저장
    ProposerDto proposer = employeeService.proposer(annualLeaveApprovalDetail.getProposerEmployeeNo());
    model.addAttribute("proposer", proposer);

    //사용자 및 결재자 정보 조회 및 저장
    proposerAndApproverInfo(request, model);

    return "submission/approvalForm/annualLeave";
  }

  //출장서식 상세 조회
  @PostMapping("detail/businessTrip")
  public String approvalDetailBusinessTrip(HttpServletRequest request, Model model) {
    //결재 서식 상세 조회 후 모델에 저장
    BusinessTripApprovalDetailForm businessTripApprovalDetail = approvalListService.businessTripApprovalDetail(request);
    businessTripApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    model.addAttribute("businessTripApprovalDetail", businessTripApprovalDetail);

    //기안자 정보 조회 및 모델에 저장
    ProposerDto proposer = employeeService.proposer(businessTripApprovalDetail.getProposerEmployeeNo());
    model.addAttribute("proposer", proposer);

    //사용자 및 결재자 정보 조회 및 저장
    proposerAndApproverInfo(request, model);

    return "submission/approvalForm/businessTrip";
  }

  //시말서 상세 조회
  @PostMapping("detail/incident")
  public String approvalDetailincident(HttpServletRequest request, Model model) {
    //결재 서식 상세 조회 후 모델에 저장
    IncidentApprovalDetailForm incidentApprovalDetail = approvalListService.incidentApprovalDetail(request);
    incidentApprovalDetail.setApprovalAble(request.getParameter("approvalAble"));
    model.addAttribute("incidentApprovalDetail", incidentApprovalDetail);

    //기안자 정보 조회 및 모델에 저장
    ProposerDto proposer = employeeService.proposer(incidentApprovalDetail.getProposerEmployeeNo());
    model.addAttribute("proposer", proposer);

    //사용자 및 결재자 정보 조회 및 저장
    proposerAndApproverInfo(request, model);

    return "submission/approvalForm/incident";
  }

  //선택 서식을 결재 처리
  @PostMapping("approve")
  public String approve(HttpServletRequest request) {

    //결재 처리
    approvalListService.approve(request);

    //다음 결재자가 있는지 확인 후 다음 결재자가 있으면 '결재중' 페이지로, 결재자가 없으면 '결재 완료'페이지로 응답
    if (request.getParameter("nextApprover") != null && !request.getParameter("nextApprover").equals("")) {
      return "redirect:/submission/approval/approvalList?approvalSection=under";
    }
    return "redirect:/submission/approval/approvalList?approvalSection=complete";
  }

  //선택 서식 반려 처리
  @PostMapping("reject")
  public String reject(HttpServletRequest request) {

    //반려 처리
    approvalListService.rejectApproval(request);

    return "redirect:/submission/approval/approvalList?approvalSection=reject";
  }

  //결재 내역 삭제
  @PostMapping("delete")
  public String delete(HttpServletRequest request) {

    //결재 내역 삭제
    approvalListService.deleteApproval(request);

    //요청을 보낸 페이지로 응답
    return "redirect:" + request.getHeader("Referer");
  }

  //기안자와 결재자, 사용자의 정보를 담는 메서드
  private void proposerAndApproverInfo(HttpServletRequest request, Model model) {
    //결재자 리스트 생성 및 모델에 저장
    List<ApproverDto> approverList = approvalListService.approverList(request);
    model.addAttribute("approverList", approverList);

    //기능에 필요한 값과 페이지에 응답할 기본값 저장
    model.addAttribute("nextApproverCheck", request.getParameter("nextApproverCheck"));
    model.addAttribute("employeeNo", request.getParameter("employeeNo"));
    model.addAttribute("approvalSection", request.getParameter("approvalSection"));
  }

}
