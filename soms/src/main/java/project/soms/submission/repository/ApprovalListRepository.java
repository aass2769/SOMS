package project.soms.submission.repository;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;

import java.util.List;

//결재 내역 및 상세 조회 , 결재 , 반려의 기능을 담은 repository인터페이스
public interface ApprovalListRepository {

  //결재가 완료되지 않은 내역만 담는 리스트
  List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  //결재가 완료된 내역만 담는 리스트
  List<SubmissionDto> completeApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  //결재가 반려된 내역만 담는 리스트
  List<SubmissionDto> rejectedApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

  //해당 서식 클릭시 미열람 상태라면 열람으로 변경
  void approvalOpen(Long submissionNo);

  //결재 처리가 완료 되면 기안자 해당 결재건에 대해서 알람으로 확인하도록 미열람으로 변경
  void comProposerOpen(Long submissionNo);

  //지출결의서 상세 내용을 담는 객체
  ExpenseApprovalDetailForm expenseApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  //연장근로신청서 상세 내용을 담는 객체
  OvertimeApprovalDetailForm overtimeApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  //연차신청서 상세 내용을 담는 객체
  AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  //출장신청서 상세 내용을 담는 객체
  BusinessTripApprovalDetailForm businessTripApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  //시말서 상세 내용을 담는 객체
  IncidentApprovalDetailForm incidentApprovalDetail(Long submissionNo, String submissionPri, Long employeeNo);

  //결재자 리스트를 담는 배열리스트
  List<ApproverDto> approverList(String submissionPri);

  //결재 클릭시 해당 서식을 결재 처리
  void approve(Long submissionNO);

  //결재 클릭시 다음결재자가 서식을 열람할 수 있도록 열람가능여부를 할당
  void nextApproverShowable(String submissionPri, Long proposerEmployeeNo);

  //반려 클릭시 해당 서식을 반려 처리
  void rejectApproval(Long submissionNo, String submissionComent);

  /**
   * 결재 내역에서 해당 서식을 보이지 않도록 삭제 처리
   * 열람 가능 여부를 '가능'에서 '불가'로 변경
   */
  void deleteApproval(Long submissionNo, String deleteValue);


}
