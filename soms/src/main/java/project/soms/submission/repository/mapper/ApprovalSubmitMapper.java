package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.soms.submission.dto.*;

@Mapper
public interface ApprovalSubmitMapper {

  Integer expenseSubmit(ExpenseDto expenseDto);

  Integer overtimeSubmit(OvertimeDto overtimeDto);

  Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto);

  Integer businessTripSubmit(BusinessTripDto businessTripDto);

  Integer incidentSubmit(IncidentDto incidentDto);

  void submissionSubmit(SubmissionDto submissionDto);

}
