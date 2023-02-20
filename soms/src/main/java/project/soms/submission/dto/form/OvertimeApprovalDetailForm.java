package project.soms.submission.dto.form;

import lombok.Data;

@Data
//연장근로신청서 상신시 데이터 검증을 위한 form클래스
public class OvertimeApprovalDetailForm {

  /**
   * 기본적인 서식 관련 데이터와
   * 연장근로 상세 내용을 담는 데이터를 선언하거나 저장함
   */
  private Long submissionNo;
  private String submissionPri;
  private String submissionSection;
  /**
   * 기본적인 서식 관련 데이터와
   * 연차 상세 내용을 담는 데이터를 선언하거나 저장함
   */
  private String submissionStatus;
  /**
   * 결재가 가능한지 아닌지를 구분하기 위한 변수
   * !!!데이터베이스 컬럼에는 없고, 쿼리에서 생성한 컬럼!!!
   */
  private String approvalAble;
  private Long proposerEmployeeNo;
  private Long approverEmployeeNo;
  private Long overtimeNo;
  private String overtimeSection;
  private String overtimePjt;
  private String overtimeStartDate;
  private Integer overtimeStartTime;
  private String overtimeEndDate;
  private Integer overtimeEndTime;
  private String overtimeContent;
  /**
   * 결재 반려를 시킬 때 기안자에게 확인 요청을 주기위한 기안자의 값이 담긴 PK값
   * 결재 반려외에 결재 완료에서 1회 사용됨
   * 해당 결재건을 미열람으로 변경하여 기안자로 접근시 해당 건을 구분함
   */
  private Long rejectValue;

  public OvertimeApprovalDetailForm(Long submissionNo, String submissionPri, String approvalAble, Long proposerEmployeeNo, Long approverEmployeeNo, Long overtimeNo, String overtimeSection, String overtimePjt, String overtimeStartDate, Integer overtimeStartTime, String overtimeEndDate, Integer overtimeEndTime, String overtimeContent) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.approvalAble = approvalAble;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.overtimeNo = overtimeNo;
    this.overtimeSection = overtimeSection;
    this.overtimePjt = overtimePjt;
    this.overtimeStartDate = overtimeStartDate;
    this.overtimeStartTime = overtimeStartTime;
    this.overtimeEndDate = overtimeEndDate;
    this.overtimeEndTime = overtimeEndTime;
    this.overtimeContent = overtimeContent;
  }

  public OvertimeApprovalDetailForm() {
  }
}
