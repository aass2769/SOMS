package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.*;
import project.soms.submission.repository.mapper.ApprovalSubmitMapper;

@Repository
@RequiredArgsConstructor
//결재 내역을 상신하는 기능을 담은 repository impl클래스
public class ApprovalSubmitRepositoryImpl implements ApprovalSubmitRepository{

  //결재 내역 insert쿼리를 담은 mapper인터페이스
  private final ApprovalSubmitMapper approvalSubmitMapper;

  //지출결의서 상신
  @Override
  public Integer expenseSubmit(ExpenseDto expenseDto) {
    return approvalSubmitMapper.expenseSubmit(expenseDto);
  }

  //연장근로신청서 상신
  @Override
  public Integer overtimeSubmit(OvertimeDto overtimeDto) {
    return approvalSubmitMapper.overtimeSubmit(overtimeDto);
  }

  //연차신청서 상신
  @Override
  public Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto) {
    return approvalSubmitMapper.annualLeaveSubmit(annualLeaveDto);
  }

  //출장신청서 상신
  @Override
  public Integer businessTripSubmit(BusinessTripDto businessTripDto) {
    return approvalSubmitMapper.businessTripSubmit(businessTripDto);
  }

  //시말서 상신
  @Override
  public Integer incidentSubmit(IncidentDto incidentDto) {
    return approvalSubmitMapper.incidentSubmit(incidentDto);
  }

  //결재 내역 저장
  @Override
  public void submissionSubmit(SubmissionDto submissionDto) {
    approvalSubmitMapper.submissionSubmit(submissionDto);
  }
}
