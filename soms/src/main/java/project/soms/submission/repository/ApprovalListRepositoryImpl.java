package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;
import project.soms.submission.repository.mapper.ApprovalListMapper;
import project.soms.submission.repository.mapper.EmployeeMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
//결재 내역 및 상세 조회 , 결재 , 반려 기능을 저장한 repository impl클래스
public class ApprovalListRepositoryImpl implements ApprovalListRepository{

  /**
   * 결재 내역 및 조회 , 결재 , 반려 쿼리를 담은 mapper인터페이스와
   * 기안자, 결재자의 정보를 조회 쿼리를 담은 mapper인터페이스
   */
  private final ApprovalListMapper approvalListMapper;
  private final EmployeeMapper employeeMapper;

  /**
   * datetime으로 설정된 데이터를 화면에 date로 나타내기 위해
   * date parsing을 시키기 위한 포맷
   */
  SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


  //결재 중인 내역을 가져오는 메서드
  @Override
  public List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime) {

    //해당 사원의 결재 내역 전체를 가져오고, 거기서 결재중 내역만 담을 새로운 배열을 생성
    List<SubmissionDto> approvalList = approvalListMapper.approvalList(employeeNo, submissionSection, submissionDatetime);
    List<SubmissionDto> underApprovalList = new ArrayList<>();

    for(SubmissionDto approval : approvalList) {
      //서식번호가 널이 아니것 / status가 0이면 반려건, 1이면 결재 중건, 2면 결재 완료건 / 열람가능 여부가 '가능'일 것
      if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("1") && (approval.getProposerShowable().equals("가능") || approval.getApproverShowable().equals("가능"))) {
        //datetime으로 저장된 값을 date(yyyy-MM-dd)로 변환
        Date date = dateParse(approval.getSubmissionDatetime());
        String submissionDate = dateFormat.format(date);
        approval.setSubmissionDatetime(submissionDate);
        underApprovalList.add(approval);
      }
    }
    return underApprovalList;
  }

  //결재 완료 내역을 가져오는 메서드
  @Override
  public List<SubmissionDto> completeApprovalList(Long employeeNo, String submissionSection, String submissionDatetime) {

    //해당 사원의 결재 내역 전체를 가져오고, 거기서 결재완료 내역만 담을 새로운 배열을 생성
    List<SubmissionDto> approvalList = approvalListMapper.approvalList(employeeNo, submissionSection, submissionDatetime);
    List<SubmissionDto> completeApprovalList = new ArrayList<>();

    for(SubmissionDto approval : approvalList) {
      //서식번호가 널이 아니것 / status가 0이면 반려건, 1이면 결재 중건, 2면 결재 완료건 / 기안자 또는 결재자 기준 열람 가능 여부 확인
      if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("2") &&
          ((!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null && approval.getApproverEmployeeNo() == null)
              || (!approval.getApproverShowable().equals("불가") && approval.getApproverEmployeeNo() != null))) {
        //datetime으로 저장된 값을 date(yyyy-MM-dd)로 변환
        Date date = dateParse(approval.getSubmissionDatetime());
        String submissionDate = dateFormat.format(date);
        approval.setSubmissionDatetime(submissionDate);

        //열람 가능 여부에 따라 이후에 삭제 기능이 변경됨
        List<SubmissionDto> submissionList = approvalListMapper.approverList(approval.getSubmissionPri());
        if (!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null && approval.getApproverEmployeeNo() == null) {
          approval.setProposerShowable(approval.getProposerShowable());
        } else if (!approval.getApproverShowable().equals("불가") && approval.getApproverEmployeeNo() != null) {
          approval.setProposerShowable(approval.getApproverShowable());
        }

        /*
          서식을 삭제할 때 열람가능여부 컬럼에 할당할 값을 설정
          최하위 결재자일 경우엔 기안이라는 값을 할당하려 기안자만 열람 가능상태로 남도록
          기안자일 경우엔 '결재'라는 값을 할당하여 최하위 결재자만 열람 가능하도록
         */
        if (submissionList.get(0).getApproverEmployeeNo().equals(approval.getApproverEmployeeNo()) && approval.getApproverShowable().equals("가능")) {
          approval.setDeleteCheck("기안");
        } else if (submissionList.get(0).getProposerEmployeeNo().equals(approval.getProposerEmployeeNo()) && approval.getProposerShowable().equals("가능")) {
          approval.setDeleteCheck("결재");
        } else {
          approval.setDeleteCheck("불가");
        }
        completeApprovalList.add(approval);
      }
    }
    return completeApprovalList;
  }

  //결재 반려 내역을 가져오는 메서드
  @Override
  public List<SubmissionDto> rejectedApprovalList(Long employeeNo, String submissionSection, String submissionDatetime) {

    //해당 사원의 결재 내역 전체를 가져오고, 거기서 결재중 내역만 담을 새로운 배열을 생성
    List<SubmissionDto> approvalList = approvalListMapper.approvalList(employeeNo, submissionSection, submissionDatetime);
    List<SubmissionDto> rejectedApprovalList = new ArrayList<>();

    for(SubmissionDto approval : approvalList) {
      //서식번호가 널이 아니것 / status가 0이면 반려건, 1이면 결재 중건, 2면 결재 완료건 / 기안자 또는 결재자 기준 열람 가능 여부 확인
      if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("0") &&
          ((!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null && approval.getApproverEmployeeNo() == null)
              || (!approval.getApproverShowable().equals("불가") && approval.getApproverEmployeeNo() != null))) {
        //datetime으로 저장된 값을 date(yyyy-MM-dd)로 변환
        Date date = dateParse(approval.getSubmissionDatetime());
        String submissionDate = dateFormat.format(date);
        approval.setSubmissionDatetime(submissionDate);

        //열람 가능 여부에 따라 이후에 삭제 기능이 변경됨
        List<SubmissionDto> submissionList = approvalListMapper.approverList(approval.getSubmissionPri());
        if (!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null && approval.getApproverEmployeeNo() == null) {
          approval.setProposerShowable(approval.getProposerShowable());
        } else if (!approval.getApproverShowable().equals("불가") && approval.getApproverEmployeeNo() != null) {
          approval.setProposerShowable(approval.getApproverShowable());
        }
        /*
          서식을 삭제할 때 열람가능여부 컬럼에 할당할 값을 설정
          최하위 결재자일 경우엔 '기안'이라는 값을 할당하여 기안자만 열람 가능상태로 남도록
          기안자일 경우엔 '결재'라는 값을 할당하여 최하위 결재자만 열람 가능하도록
         */
        if (submissionList.get(0).getApproverEmployeeNo().equals(approval.getApproverEmployeeNo()) && approval.getApproverShowable().equals("가능")) {
          approval.setDeleteCheck("기안");
        } else if (submissionList.get(0).getProposerEmployeeNo().equals(approval.getProposerEmployeeNo()) && approval.getProposerShowable().equals("가능")) {
          approval.setDeleteCheck("결재");
        } else {
          approval.setDeleteCheck("불가");
        }
        rejectedApprovalList.add(approval);

      }
    }
    return rejectedApprovalList;
  }

  //상세 보기를 위해 클릭시 해당 서식 '미열람'에서 '열람'으로 변경
  @Override
  public void approvalOpen(Long submissionNo) {
    approvalListMapper.approvalOpen(submissionNo);
  }

  //결재가 완료되면 기안자에게 알람이 갈 수 있도록 '미열람'으로 변경
  @Override
  public void comProposerOpen(Long submissionNo) {
    approvalListMapper.comProposerOpen(submissionNo);
  }

  //지출결의서 상세 내용
  @Override
  public ExpenseApprovalDetailForm expenseApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo) {

    //결재 내역을 가져온 리스트에서 본인에게 맞는 조건의 내역을 담기 위한 객체 생성
    List<ExpenseApprovalDetailForm> expenseApprovalDetails = approvalListMapper.expenseApprovalDetail(submissionNo, submissionPri);
    ExpenseApprovalDetailForm expenseApprovalDetail = new ExpenseApprovalDetailForm();

    //조건 : 결재자 위치에 있는 사번과 본인의 사번이 동일할 것
    for (ExpenseApprovalDetailForm i : expenseApprovalDetails){
      if (i.getApproverEmployeeNo().equals(employeeNo)) {
        expenseApprovalDetail = new ExpenseApprovalDetailForm(i.getSubmissionNo(), i.getSubmissionPri(), i.getApprovalAble(),
            i.getProposerEmployeeNo(), i.getApproverEmployeeNo(), i.getExpenseNo(), i.getExpenseSection(), i.getExpensePjt(),
            i.getExpenseDate(), i.getExpenseCost(), i.getExpenseContent());
      }
    }
    //만약 값이 담기지 않았다면 기안자가 조회하는 것이니 기안자의 내역을 담음
    if (expenseApprovalDetail.getSubmissionPri() == null) {
      expenseApprovalDetail = new ExpenseApprovalDetailForm(expenseApprovalDetails.get(0).getSubmissionNo(),
          expenseApprovalDetails.get(0).getSubmissionPri(), expenseApprovalDetails.get(0).getApprovalAble(),
          expenseApprovalDetails.get(0).getProposerEmployeeNo(), expenseApprovalDetails.get(0).getApproverEmployeeNo(),
          expenseApprovalDetails.get(0).getExpenseNo(), expenseApprovalDetails.get(0).getExpenseSection(), expenseApprovalDetails.get(0).getExpensePjt(),
          expenseApprovalDetails.get(0).getExpenseDate(), expenseApprovalDetails.get(0).getExpenseCost(), expenseApprovalDetails.get(0).getExpenseContent());
    }
    expenseApprovalDetail.setProposerEmployeeNo(expenseApprovalDetails.get(0).getProposerEmployeeNo());
    //반려 혹은 전체 결재 완료시 기안자에게 알람을 주기 위한 서식번호를 저장
    expenseApprovalDetail.setRejectValue(expenseApprovalDetails.get(0).getSubmissionNo());
    return expenseApprovalDetail;
  }

  //연장근로신청서 상세 내용
  @Override
  public OvertimeApprovalDetailForm overtimeApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo) {

    //결재 내역을 가져온 리스트에서 본인에게 맞는 조건의 내역을 담기 위한 객체 생성
    List<OvertimeApprovalDetailForm> overtimeApprovalDetails = approvalListMapper.overtimeApprovalDetail(submissionNo, submissionPri);
    OvertimeApprovalDetailForm overtimeApprovalDetail = new OvertimeApprovalDetailForm();

    //조건 : 결재자 위치에 있는 사번과 본인의 사번이 동일할 것
    for (OvertimeApprovalDetailForm i : overtimeApprovalDetails){
      if (i.getApproverEmployeeNo().equals(employeeNo)) {
        overtimeApprovalDetail = new OvertimeApprovalDetailForm(i.getSubmissionNo(), i.getSubmissionPri(), i.getApprovalAble(),
            i.getProposerEmployeeNo(), i.getApproverEmployeeNo(), i.getOvertimeNo(), i.getOvertimeSection(), i.getOvertimePjt(),
            i.getOvertimeStartDate(), i.getOvertimeStartTime(), i.getOvertimeEndDate(), i.getOvertimeEndTime(), i.getOvertimeContent());
      }
    }
    //만약 값이 담기지 않았다면 기안자가 조회하는 것이니 기안자의 내역을 담음
    if (overtimeApprovalDetail.getSubmissionPri() == null) {
      overtimeApprovalDetail = new OvertimeApprovalDetailForm(overtimeApprovalDetails.get(0).getSubmissionNo(), overtimeApprovalDetails.get(0).getSubmissionPri(),
          overtimeApprovalDetails.get(0).getApprovalAble(), overtimeApprovalDetails.get(0).getProposerEmployeeNo(), overtimeApprovalDetails.get(0).getApproverEmployeeNo(),
          overtimeApprovalDetails.get(0).getOvertimeNo(), overtimeApprovalDetails.get(0).getOvertimeSection(), overtimeApprovalDetails.get(0).getOvertimePjt(),
          overtimeApprovalDetails.get(0).getOvertimeStartDate(), overtimeApprovalDetails.get(0).getOvertimeStartTime(), overtimeApprovalDetails.get(0).getOvertimeEndDate(),
          overtimeApprovalDetails.get(0).getOvertimeEndTime(), overtimeApprovalDetails.get(0).getOvertimeContent());
    }
    overtimeApprovalDetail.setProposerEmployeeNo(overtimeApprovalDetails.get(0).getProposerEmployeeNo());
    //반려 혹은 전체 결재 완료시 기안자에게 알람을 주기 위한 서식번호를 저장
    overtimeApprovalDetail.setRejectValue(overtimeApprovalDetails.get(0).getSubmissionNo());
    return overtimeApprovalDetail;
  }

  //연차신청서 상세 내용
  @Override
  public AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo) {

    //결재 내역을 가져온 리스트에서 본인에게 맞는 조건의 내역을 담기 위한 객체 생성
    List<AnnualLeaveApprovalDetailForm> annualLeaveApprovalDetails = approvalListMapper.annualLeaveApprovalDetail(submissionNo, submissionPri);
    AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail = new AnnualLeaveApprovalDetailForm();

    //조건 : 결재자 위치에 있는 사번과 본인의 사번이 동일할 것
    for (AnnualLeaveApprovalDetailForm i : annualLeaveApprovalDetails){
      if (i.getApproverEmployeeNo().equals(employeeNo)) {
        annualLeaveApprovalDetail = new AnnualLeaveApprovalDetailForm(i.getSubmissionNo(), i.getSubmissionPri(), i.getApprovalAble(),
            i.getProposerEmployeeNo(), i.getApproverEmployeeNo(), i.getAnnualLeaveNo(), i.getAnnualLeaveSection(), i.getAnnualLeavePjt(),
            i.getAnnualLeaveStart(), i.getAnnualLeaveEnd(), i.getAnnualLeaveTime(), i.getAnnualLeaveContent());
      }
    }
    //만약 값이 담기지 않았다면 기안자가 조회하는 것이니 기안자의 내역을 담음
    if (annualLeaveApprovalDetail.getSubmissionPri() == null) {
      annualLeaveApprovalDetail = new AnnualLeaveApprovalDetailForm(annualLeaveApprovalDetails.get(0).getSubmissionNo(), annualLeaveApprovalDetails.get(0).getSubmissionPri(),
          annualLeaveApprovalDetails.get(0).getApprovalAble(), annualLeaveApprovalDetails.get(0).getProposerEmployeeNo(), annualLeaveApprovalDetails.get(0).getApproverEmployeeNo(),
          annualLeaveApprovalDetails.get(0).getAnnualLeaveNo(), annualLeaveApprovalDetails.get(0).getAnnualLeaveSection(), annualLeaveApprovalDetails.get(0).getAnnualLeavePjt(),
          annualLeaveApprovalDetails.get(0).getAnnualLeaveStart(), annualLeaveApprovalDetails.get(0).getAnnualLeaveEnd(), annualLeaveApprovalDetails.get(0).getAnnualLeaveTime(),
          annualLeaveApprovalDetails.get(0).getAnnualLeaveContent());
    }
    annualLeaveApprovalDetail.setProposerEmployeeNo(annualLeaveApprovalDetails.get(0).getProposerEmployeeNo());
    //반려 혹은 전체 결재 완료시 기안자에게 알람을 주기 위한 서식번호를 저장
    annualLeaveApprovalDetail.setRejectValue(annualLeaveApprovalDetails.get(0).getSubmissionNo());
    return annualLeaveApprovalDetail;
  }

  //출장신청서 상세 내용
  @Override
  public BusinessTripApprovalDetailForm businessTripApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo) {

    //결재 내역을 가져온 리스트에서 본인에게 맞는 조건의 내역을 담기 위한 객체 생성
    List<BusinessTripApprovalDetailForm> businessTripApprovalDetails = approvalListMapper.businessTripApprovalDetail(submissionNo, submissionPri);
    BusinessTripApprovalDetailForm businessTripApprovalDetail = new BusinessTripApprovalDetailForm();

    //조건 : 결재자 위치에 있는 사번과 본인의 사번이 동일할 것
    for (BusinessTripApprovalDetailForm i : businessTripApprovalDetails){
      if (i.getApproverEmployeeNo().equals(employeeNo)) {
        businessTripApprovalDetail = new BusinessTripApprovalDetailForm(i.getSubmissionNo(), i.getSubmissionPri(), i.getApprovalAble(),
            i.getProposerEmployeeNo(), i.getApproverEmployeeNo(), i.getBusinessTripNo(), i.getBusinessTripSection(), i.getBusinessTripPjt(),
            i.getBusinessTripStart(), i.getBusinessTripEnd(), i.getBusinessTripTime(), i.getBusinessTripDestination(), i.getBusinessTripContent());
      }
    }
    //만약 값이 담기지 않았다면 기안자가 조회하는 것이니 기안자의 내역을 담음
    if (businessTripApprovalDetail.getSubmissionPri() == null) {
      businessTripApprovalDetail = new BusinessTripApprovalDetailForm(businessTripApprovalDetails.get(0).getSubmissionNo(), businessTripApprovalDetails.get(0).getSubmissionPri(),
          businessTripApprovalDetails.get(0).getApprovalAble(), businessTripApprovalDetails.get(0).getProposerEmployeeNo(), businessTripApprovalDetails.get(0).getApproverEmployeeNo(),
          businessTripApprovalDetails.get(0).getBusinessTripNo(), businessTripApprovalDetails.get(0).getBusinessTripSection(), businessTripApprovalDetails.get(0).getBusinessTripPjt(),
          businessTripApprovalDetails.get(0).getBusinessTripStart(), businessTripApprovalDetails.get(0).getBusinessTripEnd(), businessTripApprovalDetails.get(0).getBusinessTripTime(),
          businessTripApprovalDetails.get(0).getBusinessTripDestination(), businessTripApprovalDetails.get(0).getBusinessTripContent());
    }
    businessTripApprovalDetail.setProposerEmployeeNo(businessTripApprovalDetails.get(0).getProposerEmployeeNo());
    //반려 혹은 전체 결재 완료시 기안자에게 알람을 주기 위한 서식번호를 저장
    businessTripApprovalDetail.setRejectValue(businessTripApprovalDetails.get(0).getSubmissionNo());
    return businessTripApprovalDetail;
  }

  //시말서 상세 내용
  @Override
  public IncidentApprovalDetailForm incidentApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo) {

    //결재 내역을 가져온 리스트에서 본인에게 맞는 조건의 내역을 담기 위한 객체 생성
    List<IncidentApprovalDetailForm> incidentApprovalDetails = approvalListMapper.incidentApprovalDetail(submissionNo, submissionPri);
    IncidentApprovalDetailForm incidentApprovalDetail = new IncidentApprovalDetailForm();

    //조건 : 결재자 위치에 있는 사번과 본인의 사번이 동일할 것
    for (IncidentApprovalDetailForm i : incidentApprovalDetails){
      if (i.getApproverEmployeeNo().equals(employeeNo)) {
        incidentApprovalDetail = new IncidentApprovalDetailForm(i.getSubmissionNo(), i.getSubmissionPri(),
            i.getApprovalAble(), i.getProposerEmployeeNo(), i.getApproverEmployeeNo(), i.getIncidentNo(),
            i.getIncidentSection(), i.getIncidentPjt(), i.getIncidentContent());
      }
    }
    //만약 값이 담기지 않았다면 기안자가 조회하는 것이니 기안자의 내역을 담음
    if (incidentApprovalDetail.getSubmissionPri() == null) {
      incidentApprovalDetail = new IncidentApprovalDetailForm(incidentApprovalDetails.get(0).getSubmissionNo(),
          incidentApprovalDetails.get(0).getSubmissionPri(), incidentApprovalDetails.get(0).getApprovalAble(),
          incidentApprovalDetails.get(0).getProposerEmployeeNo(), incidentApprovalDetails.get(0).getApproverEmployeeNo(),
          incidentApprovalDetails.get(0).getIncidentNo(), incidentApprovalDetails.get(0).getIncidentSection(),
          incidentApprovalDetails.get(0).getIncidentPjt(), incidentApprovalDetails.get(0).getIncidentContent());
    }
    incidentApprovalDetail.setProposerEmployeeNo(incidentApprovalDetails.get(0).getProposerEmployeeNo());
    //반려 혹은 전체 결재 완료시 기안자에게 알람을 주기 위한 서식번호를 저장
    incidentApprovalDetail.setRejectValue(incidentApprovalDetails.get(0).getSubmissionNo());
    return incidentApprovalDetail;
  }

  //결재자 리스트 생성
  @Override
  public List<ApproverDto> approverList(String submissionPri) {

    //서식 번호 값으로 결재자건 조회
    List<SubmissionDto> submissionDtos = approvalListMapper.approverList(submissionPri);
    List<ApproverDto> approverList = new ArrayList<>();

    //결재자 정보 조회 및 결재자 리스트에 결재자 정호 할당
    for (SubmissionDto detailList : submissionDtos) {
      ProposerDto getApproverName = employeeMapper.proposer(detailList.getApproverEmployeeNo());
      approverList.add(new ApproverDto(detailList.getApproverEmployeeNo(), getApproverName.getEmployeeName(),
          detailList.getSubmissionSection(), detailList.getSubmissionStatus()));
    }
    return approverList;
  }

  //서식 결재 처리
  @Override
  public void approve(Long submissionNo) {
    approvalListMapper.approve(submissionNo);
  }

  //다음 결재자가 열람할 수 있도록
  @Override
  public void nextApproverShowable(String submissionPri, Long approverEmployeeNo) {
    approvalListMapper.nextApproverShowable(submissionPri, approverEmployeeNo);
  }

  //서식 반려 처리
  @Override
  public void rejectApproval(Long submissionNo, String submissionComent) {
    approvalListMapper.rejectApproval(submissionNo, submissionComent);
  }

  //결재 내역에서 해당 서식 삭제
  @Override
  public void deleteApproval(Long submissionNo, String deleteValue) {
    approvalListMapper.deleteApproval(submissionNo, deleteValue);
  }


  //datetime을 date로 변화하기 위해 해당 값을 date타입으로 parsing 함
  private Date dateParse(String submissionDatetime) {
    Date date = null;
    try {
      date = datetimeFormat.parse(submissionDatetime);
    } catch (ParseException e) {
      log.warn("submissionDatetime parse fail ={}", e);
    }
    return date;
  }
}
