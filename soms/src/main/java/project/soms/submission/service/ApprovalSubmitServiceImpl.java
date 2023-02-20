package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.soms.submission.dto.*;
import project.soms.submission.repository.ApprovalSubmitRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
//결재 상신을 처리하는 service impl클래스
public class ApprovalSubmitServiceImpl implements ApprovalSubmitService{

  /**
   * 결재 상신 insert 기능를 담은 repository인터페이스와
   * 기안자 결재자 정보를 조회하는 repository인터페이스
   */
  private final ApprovalSubmitRepository approvalSubmitRepository;
  private final EmployeeService employeeService;

  //지출결의서 상신 메서드
  @Override
  public void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo,
                            List<ApproverDto> approverDto) {

    //받아온 지출결의서의 값을 데이터에 추가
    approvalSubmitRepository.expenseSubmit(expenseDto);

    //결재서식에 필요한 값을 할당. 지출결의서 pk, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setExpenseNo(expenseDto.getExpenseNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, proposerDto);
  }

  //연장근로신청서 상신 메서드
  @Override
  public void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo,
                             List<ApproverDto> approverDto) {
    //받아온 연장근로신청서의 값을 데이터에 추가
    approvalSubmitRepository.overtimeSubmit(overtimeDto);

    //결재서식에 필요한 값을 할당. 지출결의서 pk, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setOvertimeNo(overtimeDto.getOvertimeNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, proposerDto);
  }

  //연차신청서 상신 메서드
  @Override
  public void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo,
                                List<ApproverDto> approverDto) {
    //받아온 연차신청서의 값을 데이터에 추가
    approvalSubmitRepository.annualLeaveSubmit(annualLeaveDto);

    //결재서식에 필요한 값을 할당. 지출결의서 pk, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setAnnualLeaveNo(annualLeaveDto.getAnnualLeaveNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, proposerDto);
  }

  //출장신청서 상신 메서드
  @Override
  public void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo,
                                 List<ApproverDto> approverDto) {
    //받아온 출장신청서의 값을 데이터에 추가
    approvalSubmitRepository.businessTripSubmit(businessTripDto);

    //결재서식에 필요한 값을 할당. 지출결의서 pk, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setBusinessTripNo(businessTripDto.getBusinessTripNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, proposerDto);
  }

  //시말서 상신 메서드
  @Override
  public void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo,
                             List<ApproverDto> approverDto) {
    //받아온 시말서의 값을 데이터에 추가
    approvalSubmitRepository.incidentSubmit(incidentDto);

    //결재서식에 필요한 값을 할당. 지출결의서 pk, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setIncidentNo(incidentDto.getIncidentNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, proposerDto);
  }

  //결재 서식 insert 메서드
  private void insertSubmission(SubmissionDto submissionDto, List<ApproverDto> approverDto, ProposerDto proposerDto) {

    //결재자의 수 만큼 insert 반복
    for (ApproverDto i : approverDto) {

      ProposerDto approver = employeeService.proposer(i.getEmployeeNo());
      //결재자의 직급이 기안자 상위에 있도록 검증
      if (proposerDto.getEmployeeNo() != approver.getEmployeeNo() && proposerDto.getManageNo() <= approver.getManageNo()) {

        //서식 구분(검토, 결재, 등등...), 결재자 사번 입력
        Long approverNo = i.getEmployeeNo();
        submissionDto.setSubmissionSection(i.getSubmissionSection());
        submissionDto.setApproverEmployeeNo(approverNo);

        //결재 서식 insert
        approvalSubmitRepository.submissionSubmit(submissionDto);
        log.info("insert submission={}", submissionDto);

        //다음 insert를 위해 이번 결재자를 다음번 기안자로 설정 및 열람 가능 여부를 불가로 변경
        submissionDto.setProposerEmployeeNo(approverNo);
        submissionDto.setSubmissionShowable("불가");
      } else {
        //관계 검증 실페시 실패 exception
        throw new IllegalArgumentException("기안자의 직급이 결재자보다 높거나 결재자에 본인을 추가했습니다.");
      }
    }
  }
}
