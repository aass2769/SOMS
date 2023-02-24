package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;
import project.soms.submission.repository.ApprovalListRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
//결재 내역 및 상세 , 결재 , 반려 기능을 담은 service impl클래스
public class ApprovalListServiceImpl implements ApprovalListService{

  //결재 내역 및 상세 , 결재 , 반려 기능을 담은 repository인터페이스
  private final ApprovalListRepository approvalListRepository;

  //결재 내역 조회, 리스트 생성
  @Override
  public List<SubmissionDto> approvalList(Long employeeNo, HttpServletRequest request) {

    //approvalSection = 결재 구분 ... 'under', 'complete', 'reject'
    String approvalListSection = request.getParameter("approvalSection");

    //검색 조건(서식 구분과 상신 일)이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    //조건에 맞는 객체만 담을 리스트 생성
    List<SubmissionDto> approvalList = new ArrayList<>();

    //'결재중', '결재 완료', '반려' 를 구분하여 요청 구분에 맞는 값을 저장
    if (approvalListSection.equals("under")) {
      approvalList = approvalListRepository.underApprovalList(employeeNo, submissionSection, submissionDatetime);
    } else if (approvalListSection.equals("complete")) {
      approvalList = approvalListRepository.completeApprovalList(employeeNo, submissionSection, submissionDatetime);
    } else if (approvalListSection.equals("reject")) {
      approvalList = approvalListRepository.rejectedApprovalList(employeeNo, submissionSection, submissionDatetime);
    }
    return approvalList;
  }

  //지출결의서 상세 내용
  @Override
  public ExpenseApprovalDetailForm expenseApprovalDetail(HttpServletRequest request) {

    //상세 내용 조회 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    /**
     * 해당 서식이 '미열람'건일 경우 '열람'으로 변경
     * '결재 중 내역'일 경우엔 '미열람'을 '열람'으로
     * '결재 완료 내역', '결재 반려 내역'일 경우엔 '본인건'을 '열람'으로
     */
    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    ExpenseApprovalDetailForm expenseApprovalDetailForm = approvalListRepository.expenseApprovalDetail(submissionNo, submissionPri, employeeNo);
    return expenseApprovalDetailForm;
  }

  //연장근로신청서 상세 내용
  @Override
  public OvertimeApprovalDetailForm overtimeApprovalDetail(HttpServletRequest request) {

    //상세 내용 조회 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    /**
     * 해당 서식이 '미열람'건일 경우 '열람'으로 변경
     * '결재 중 내역'일 경우엔 '미열람'을 '열람'으로
     * '결재 완료 내역', '결재 반려 내역'일 경우엔 '본인건'을 '열람'으로
     */
    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    OvertimeApprovalDetailForm overtimeApprovalDetailForm = approvalListRepository.overtimeApprovalDetail(submissionNo, submissionPri, employeeNo);
    return overtimeApprovalDetailForm;
  }

  //연차신청서 상세 내용
  @Override
  public AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail(HttpServletRequest request) {

    //상세 내용 조회 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    /**
     * 해당 서식이 '미열람'건일 경우 '열람'으로 변경
     * '결재 중 내역'일 경우엔 '미열람'을 '열람'으로
     * '결재 완료 내역', '결재 반려 내역'일 경우엔 '본인건'을 '열람'으로
     */
    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    AnnualLeaveApprovalDetailForm annualLeaveApprovalDetailForm = approvalListRepository.annualLeaveApprovalDetail(submissionNo, submissionPri, employeeNo);
    return annualLeaveApprovalDetailForm;
  }

  //출장신청서 상세 내용
  @Override
  public BusinessTripApprovalDetailForm businessTripApprovalDetail(HttpServletRequest request) {

    //상세 내용 조회 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    /**
     * 해당 서식이 '미열람'건일 경우 '열람'으로 변경
     * '결재 중 내역'일 경우엔 '미열람'을 '열람'으로
     * '결재 완료 내역', '결재 반려 내역'일 경우엔 '본인건'을 '열람'으로
     */
    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    BusinessTripApprovalDetailForm businessTripApprovalDetailForm = approvalListRepository.businessTripApprovalDetail(submissionNo, submissionPri, employeeNo);
    return businessTripApprovalDetailForm;
  }

  //시말서 상세 내용
  @Override
  public IncidentApprovalDetailForm incidentApprovalDetail(HttpServletRequest request) {

    //상세 내용 조회 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    String submissionOpen = request.getParameter("submissionOpen");
    String section = request.getParameter("approvalSection");
    Long employeeNo = Long.valueOf(request.getParameter("employeeNo"));

    /**
     * 해당 서식이 '미열람'건일 경우 '열람'으로 변경
     * '결재 중 내역'일 경우엔 '미열람'을 '열람'으로
     * '결재 완료 내역', '결재 반려 내역'일 경우엔 '본인건'을 '열람'으로
     */
    if (section.equals("under") && submissionOpen.equals("미열람")) {
      approvalListRepository.approvalOpen(submissionNo);
    } else if (section.equals("complete") || section.equals("reject") && submissionOpen.equals("본인건")) {
      approvalListRepository.approvalOpen(submissionNo);
    }

    IncidentApprovalDetailForm incidentApprovalDetailForm = approvalListRepository.incidentApprovalDetail(submissionNo, submissionPri, employeeNo);
    return incidentApprovalDetailForm;
  }

  //결재자 리스트
  @Override
  public List<ApproverDto> approverList(HttpServletRequest request) {

    //서식 번호를 선언
    String submissionPri = request.getParameter("submissionPri");

    //서식 리스트 생성 후 반환
    return approvalListRepository.approverList(submissionPri);
  }

  //결재 처리
  @Transactional
  @Override
  public void approve(HttpServletRequest request) {

    //결재 처리 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionPri = request.getParameter("submissionPri");
    Long approverEmployeeNo = Long.valueOf(request.getParameter("approverEmployeeNo"));

    /**
     * 결재 처리 실행
     * 다음 결재자가 있는지 확인
     * 다음 결재자가 있으면 다음 결재자의 열람 가능 여부를 '가능'으로 변경
     * 다음 결재자가 없으면 기안자의 열람 상태를 '미열람'으로 변경
     */
    approvalListRepository.approve(submissionNo);
    if (request.getParameter("nextApprover") != null && !request.getParameter("nextApprover").equals("")) {
      approvalListRepository.nextApproverShowable(submissionPri, approverEmployeeNo);
    } else {
      approvalListRepository.comProposerOpen(Long.valueOf(request.getParameter("proposerValue")));
    }
  }

  //결재 반려 처리
  @Transactional
  @Override
  public void rejectApproval(HttpServletRequest request) {

    //결재 반려 조건에 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String submissionComent = request.getParameter("submissionComent");
    String submissionPri = request.getParameter("submissionPri");
    Long approverEmployeeNo = Long.valueOf(request.getParameter("approverEmployeeNo"));

    /**
     * 결재 반려 처리 실행
     * 기안자의 열람 상태를 '미열람'으로 변경
     */
    approvalListRepository.rejectApproval(submissionNo, submissionComent);
    approvalListRepository.comProposerOpen(Long.valueOf(request.getParameter("rejectValue")));
  }

  //결재 내역 삭제
  @Override
  public void deleteApproval(HttpServletRequest request) {

    //결재 삭제를 위해 필요한 값 선언
    Long submissionNo = Long.valueOf(request.getParameter("submissionNo"));
    String saveProposer = request.getParameter("saveProposer");

    /**
     * 삭제할 때 해당 서식의 삭제자가 최하위 결재자일 땐 '기안'으로 값 할당
     * 아니면 '불가'로 값 할당
     */
    if (saveProposer.equals("기안")) {
      approvalListRepository.deleteApproval(submissionNo, "기안");
    } else if (saveProposer.equals("결재")){
      approvalListRepository.deleteApproval(submissionNo, "결재");
    } else {
      approvalListRepository.deleteApproval(submissionNo, "불가");
    }

  }
}
