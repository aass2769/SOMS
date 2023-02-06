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

  @Override
  public void expenseSubmit(SubmissionDto submissionDto, ExpenseDto expenseDto, Long proposerEmployeeNo,
                            List<ApproverDto> approverDto, List<String> submissionSection) {

    approvalSubmitRepository.expenseSubmit(expenseDto);

    submissionDto.setExpenseNo(expenseDto.getExpenseNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");
    ProposerDto proposerDto = employeeService.proposer(proposerEmployeeNo);


    insertSubmission(submissionDto, approverDto, submissionSection, proposerDto);
  }

  @Override
  public void overtimeSubmit(SubmissionDto submissionDto, OvertimeDto overtimeDto, Long proposerEmployeeNo,
                             List<ApproverDto> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.overtimeSubmit(overtimeDto);

    submissionDto.setOvertimeNo(overtimeDto.getOvertimeNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");

    for (int i = 0; i < approverDto.size(); i++) {
      Long approverNo = approverDto.get(i).getEmployeeNo();
      submissionDto.setSubmissionSection(submissionSection.get(i));
      submissionDto.setApproverEmployeeNo(approverNo);

      approvalSubmitRepository.submissionSubmit(submissionDto);

      submissionDto.setProposerEmployeeNo(approverNo);
      submissionDto.setSubmissionShowable("불가");
    }
  }

  @Override
  public void annualLeaveSubmit(SubmissionDto submissionDto, AnnualLeaveDto annualLeaveDto, Long proposerEmployeeNo,
                                List<ApproverDto> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.annualLeaveSubmit(annualLeaveDto);

    submissionDto.setAnnualLeaveNo(annualLeaveDto.getAnnualLeaveNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");

    for (int i = 0; i < approverDto.size(); i++) {
      Long approverNo = approverDto.get(i).getEmployeeNo();
      submissionDto.setSubmissionSection(submissionSection.get(i));
      submissionDto.setApproverEmployeeNo(approverNo);

      approvalSubmitRepository.submissionSubmit(submissionDto);

      submissionDto.setProposerEmployeeNo(approverNo);
      submissionDto.setSubmissionShowable("불가");
    }
  }

  @Override
  public void businessTripSubmit(SubmissionDto submissionDto, BusinessTripDto businessTripDto, Long proposerEmployeeNo,
                                 List<ApproverDto> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.businessTripSubmit(businessTripDto);

    submissionDto.setBusinessTripNo(businessTripDto.getBusinessTripNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");

    for (int i = 0; i < approverDto.size(); i++) {
      Long approverNo = approverDto.get(i).getEmployeeNo();
      submissionDto.setSubmissionSection(submissionSection.get(i));
      submissionDto.setApproverEmployeeNo(approverNo);

      approvalSubmitRepository.submissionSubmit(submissionDto);

      submissionDto.setProposerEmployeeNo(approverNo);
      submissionDto.setSubmissionShowable("불가");
    }
  }

  @Override
  public void incidentSubmit(SubmissionDto submissionDto, IncidentDto incidentDto, Long proposerEmployeeNo,
                             List<ApproverDto> approverDto, List<String> submissionSection) {
    approvalSubmitRepository.incidentSubmit(incidentDto);

    submissionDto.setIncidentNo(incidentDto.getIncidentNo());
    submissionDto.setProposerEmployeeNo(proposerEmployeeNo);
    submissionDto.setSubmissionShowable("가능");

    for (int i = 0; i < approverDto.size(); i++) {
      Long approverNo = approverDto.get(i).getEmployeeNo();
      submissionDto.setSubmissionSection(submissionSection.get(i));
      submissionDto.setApproverEmployeeNo(approverNo);

      approvalSubmitRepository.submissionSubmit(submissionDto);

      submissionDto.setProposerEmployeeNo(approverNo);
      submissionDto.setSubmissionShowable("불가");
    }
  }

  private void insertSubmission(SubmissionDto submissionDto, List<ApproverDto> approverDto, List<String> submissionSection, ProposerDto proposerDto) {
    for (int i = 0; i < approverDto.size(); i++) {

      Long approverManageNo = employeeService.proposer(approverDto.get(i).getEmployeeNo()).getManageNo();

      if (proposerDto.getManageNo() <= approverManageNo) {

        Long approverNo = approverDto.get(i).getEmployeeNo();
        submissionDto.setSubmissionSection(submissionSection.get(i));
        submissionDto.setApproverEmployeeNo(approverNo);

        approvalSubmitRepository.submissionSubmit(submissionDto);
        log.info("insert submission={}", submissionDto);

        submissionDto.setProposerEmployeeNo(approverNo);
        submissionDto.setSubmissionShowable("불가");
      } else {
        throw new IllegalArgumentException("기안자의 직급이 결재자보다 높음");
      }
    }
  }
}
