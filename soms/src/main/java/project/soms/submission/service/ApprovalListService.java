package project.soms.submission.service;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.dto.form.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//결재 내역 및 상세 , 결재 , 반려 기능을 담은 service인터페이스
public interface ApprovalListService {

  //결재 내역 조회
  List<SubmissionDto> approvalList(Long employeeNo, HttpServletRequest request);

  //지출결의서 상세 내용
  ExpenseApprovalDetailForm expenseApprovalDetail(HttpServletRequest request);

  //연장근로신청서 상세 내용
  OvertimeApprovalDetailForm overtimeApprovalDetail(HttpServletRequest request);

  //연차신청서 상세 내용
  AnnualLeaveApprovalDetailForm annualLeaveApprovalDetail(HttpServletRequest request);

  //출장신청서 상세 내용
  BusinessTripApprovalDetailForm businessTripApprovalDetail(HttpServletRequest request);

  //시말서 상세 내용
  IncidentApprovalDetailForm incidentApprovalDetail(HttpServletRequest request);

  //해당 서식의 결재자 리스트
  List<ApproverDto> approverList(HttpServletRequest request);

  //결재 처리
  void approve(HttpServletRequest request);

  //반려 처리
  void rejectApproval(HttpServletRequest request);

  //삭제 처리(열람 가능 여부를 변경)
  void deleteApproval(HttpServletRequest request);
}
