package project.soms.submission.repository;

import org.springframework.stereotype.Repository;
import project.soms.submission.dto.*;

@Repository
public interface ApprovalSubmitRepository {

    Integer expenseSubmit(ExpenseDto expenseDto);

    Integer overtimeSubmit(OvertimeDto overtimeDto);

    Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto);

    Integer businessTripSubmit(BusinessTripDto businessTripDto);

    Integer incidentSubmit(IncidentDto incidentDto);

    void submissionSubmit(SubmissionDto submissionDto);
}
