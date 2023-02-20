package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.soms.submission.dto.*;

@Mapper
//결재 서식을 저장하는 쿼리를 담은 mapper인터페이스
public interface ApprovalSubmitMapper {

  //지출결의서 insert
  Integer expenseSubmit(ExpenseDto expenseDto);

  //연장근로신청서 insert
  Integer overtimeSubmit(OvertimeDto overtimeDto);

  //연차신청서 insert
  Integer annualLeaveSubmit(AnnualLeaveDto annualLeaveDto);

  //출장신청서 insert
  Integer businessTripSubmit(BusinessTripDto businessTripDto);

  //시말서 insert
  Integer incidentSubmit(IncidentDto incidentDto);

  /**
   * 위의 쿼리 실행 후 반환된 서식 pk값을 할당 후
   * 결재 기본정보를 함께 담아서 insert
   */
  void submissionSubmit(SubmissionDto submissionDto);

}
