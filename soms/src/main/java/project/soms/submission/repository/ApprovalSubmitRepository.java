package project.soms.submission.repository;

import org.springframework.stereotype.Repository;
import project.soms.submission.dto.*;

@Repository
//결재 내역을 상신하는 기능을 담은 repository인터페이스
public interface ApprovalSubmitRepository {

    //지출결의서 상신
    Integer expenseSubmit(ExpenseDto expenseDto);

    //연장근로신청서 상신
    Integer overtimeSubmit(OvertimeDto overtimeDto);

    //연차신청서 상신
    Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto);

    //출장신청서 상신
    Integer businessTripSubmit(BusinessTripDto businessTripDto);

    //시말서 상신
    Integer incidentSubmit(IncidentDto incidentDto);

    //결재 정보 저장
    void submissionSubmit(SubmissionDto submissionDto);
}
