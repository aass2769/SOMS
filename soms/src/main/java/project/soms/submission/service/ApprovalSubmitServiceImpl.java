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
public class ApprovalSubmitServiceImpl implements ApprovalSubmitService{

  private final ApprovalSubmitRepository approvalSubmitRepository;
  private final EmployeeService employeeService;

  //지출 결의서 상신 메서드
  @Override
  public void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo,
                            List<Long> approverDto, List<String> submissionSection) {

    //받아온 지출결의서의 값을 데이터에 추가
    approvalSubmitRepository.expenseSubmit(expenseDto);

    //결재서식에 필요한 값을 할당. 지출결의서 PK, 기안자의 번호, 서식 열람 가능 여부
    submissionDto.setExpenseNo(expenseDto.getExpenseNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    submissionDto.setSubmissionStatus("대기");
    submissionDto.setSubmissionOpen("미열람");

    //결재자와 기안자의 관계 검증을 위해 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    //위의 정보를 입력하여 결재 서식 insert
    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  @Override
  public void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo,
                             List<Long> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.overtimeSubmit(overtimeDto);

    submissionDto.setOvertimeNo(overtimeDto.getOvertimeNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  @Override
  public void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo,
                                List<Long> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.annualLeaveSubmit(annualLeaveDto);

    submissionDto.setAnnualLeaveNo(annualLeaveDto.getAnnualLeaveNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  @Override
  public void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo,
                                 List<Long> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.businessTripSubmit(businessTripDto);

    submissionDto.setBusinessTripNo(businessTripDto.getBusinessTripNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  @Override
  public void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo,
                             List<Long> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.incidentSubmit(incidentDto);

    submissionDto.setIncidentNo(incidentDto.getIncidentNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);

    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  //결재 서식 insert 메서드
  private void insertSubmission(SubmissionDto submissionDto, List<Long> approverDto, List<String> submissionSection, ProposerDto proposerDto) {

    //결재자의 수 만큼 insert 반복
    for (int i = 0; i < approverDto.size(); i++) {

      //기안자와 결재자의 관계를 증명하기 위해 결재자 정보 조회
      Long approverManageNo = employeeService.proposer(approverDto.get(i)).getManageNo();
      Long approverEmoloyeeNo = employeeService.proposer(approverDto.get(i)).getEmployeeNo();

      //결재자의 직급이 기안자 상위에 있도록 검증
      if (proposerDto.getEmployeeNo() != approverEmoloyeeNo && proposerDto.getManageNo() <= approverManageNo) {

        //서식 구분(검토, 결재, 등등...), 결재자 사번 입력
        Long approverNo = approverDto.get(i);
        submissionDto.setSubmissionSection(submissionSection.get(i));
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
