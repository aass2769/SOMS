package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.*;
import project.soms.submission.repository.mapper.ApprovalSubmitMapper;

@Repository
@RequiredArgsConstructor
public class ApprovalSubmitRepositoryImpl implements ApprovalSubmitRepository{

  private final ApprovalSubmitMapper approvalSubmitMapper;

  @Override
  public Integer expenseSubmit(ExpenseDto expenseDto) {
    return approvalSubmitMapper.expenseSubmit(expenseDto);
  }

  @Override
  public Integer overtimeSubmit(OvertimeDto overtimeDto) {
    return approvalSubmitMapper.overtimeSubmit(overtimeDto);
  }

  @Override
  public Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto) {
    return approvalSubmitMapper.annualLeaveSubmit(annualLeaveDto);
  }

  @Override
  public Integer businessTripSubmit(BusinessTripDto businessTripDto) {
    return approvalSubmitMapper.businessTripSubmit(businessTripDto);
  }

  @Override
  public Integer incidentSubmit(IncidentDto incidentDto) {
    return approvalSubmitMapper.incidentSubmit(incidentDto);
  }

  @Override
  public void submissionSubmit(SubmissionDto submissionDto) {
    approvalSubmitMapper.submissionSubmit(submissionDto);
  }
}
