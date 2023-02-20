package project.soms.submission.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;

import java.util.List;

@Mapper
//결재내역과 상세를 조회하고 결재 및 반려 쿼리를 담은 mapper인터페이스
public interface ApprovalListMapper {

  /**
   * 결재자 내역 리스트를 반화하는 쿼리
   * 사번으로 내역을 조회
   * 결재 구분과 날짜를 선택하여 값이 오면 조건에 추가
   */
  List<SubmissionDto> approvalList(@Param("employeeNo") long employeeNo,
                                        @Param("submissionSection") String subnissionSection,
                                        @Param("submissionDatetime") String submissionDatetime);

  /**
   * 결재 서식 열람시 미열람에서 열람으로 update
   * 결재 내역에서 미열람과 열람건을 구분하여 화면에 나타냄
   */
  void approvalOpen(@Param("submissionNo") Long submissionNo);

  /**
   * 결재 완료시 기안자의 데이터에만 미열람으로 변경하여 기안자에게 미확인 알람이 갈 수 있도록 함
   */
  void comProposerOpen(@Param("submissionNo") Long submissionNo);

  /**
   * 지출결의서 상세를 가져오기 위한 쿼리
   * 열람하는 사람이 결재자로 있는 건을 비교하여 저장하기 위해 리스트로 반환
   * (이후에 repository에서 값을 비교하여 저장)
   */
  List<ExpenseApprovalDetailForm> expenseApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                            @Param("submissionPri") String submissionPri);
  /**
   * 연장근로신청서 상세를 가져오기 위한 쿼리
   * 열람하는 사람이 결재자로 있는 건을 비교하여 저장하기 위해 리스트로 반환
   * (이후에 repository에서 값을 비교하여 저장)
   */
  List<OvertimeApprovalDetailForm> overtimeApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                         @Param("submissionPri") String submissionPri);
  /**
   * 연차신청서 상세를 가져오기 위한 쿼리
   * 열람하는 사람이 결재자로 있는 건을 비교하여 저장하기 위해 리스트로 반환
   * (이후에 repository에서 값을 비교하여 저장)
   */
  List<AnnualLeaveApprovalDetailForm> annualLeaveApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                                @Param("submissionPri") String submissionPri);  /**
   * 출장신청서 상세를 가져오기 위한 쿼리
   * 열람하는 사람이 결재자로 있는 건을 비교하여 저장하기 위해 리스트로 반환
   * (이후에 repository에서 값을 비교하여 저장)
   */
  List<BusinessTripApprovalDetailForm> businessTripApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                                @Param("submissionPri") String submissionPri);  /**
   * 시말서 상세를 가져오기 위한 쿼리
   * 열람하는 사람이 결재자로 있는 건을 비교하여 저장하기 위해 리스트로 반환
   * (이후에 repository에서 값을 비교하여 저장)
   */
  List<IncidentApprovalDetailForm> incidentApprovalDetail(@Param("submissionNo") Long submissionNo,
                                                          @Param("submissionPri") String submissionPri);

  /**
   * 결재자 리스트를 만들기 위한 쿼리
   */
  List<SubmissionDto> approverList(@Param("submissionPri") String submissionPri);

  /**
   * 결재자의 결재 처리를 위한 쿼리
   * 결재건에 대해서 '대기'를 '결재'로 update
   */
  void approve(@Param("submissionNo") Long submissionNo);

  /**
   * 다음 결재자가 열람할 수 있도록
   * 열람 가능 상태를 '불가'에서 '가능'으로 update
   */
  void nextApproverShowable(@Param("submissionPri") String submissionPri, @Param("proposerEmployeeNo") Long proposerEmployeeNo);

  /**
   * 결재건을 반려시키기 위한 쿼리
   * 결재 상태를 '대기'에서 '반려'로 update
   */
  void rejectApproval(@Param("submissionNo") Long submissionNo, @Param("submissionComent") String submissionComent);

  /**
   * 해당 결재건을 결재 내역에 나타나지 않도록 삭제시키는 쿼리
   * 열람 가능 상태를 '가능'에서 '불가'로 update
   * 혹은 '가능'에서 '기안'으로 update
   */
  void deleteApproval(@Param("submissionNo") Long submissionNo, @Param("deleteValue") String deleteValue);
}