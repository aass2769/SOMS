package project.soms.submission.dto.form;

import lombok.Data;

@Data
//연차 서식을 조회할 때 사용되는 data클래스
public class AnnualLeaveApprovalDetailForm {

  /**
   * 기본적인 서식 관련 데이터와
   * 연차 상세 내용을 담는 데이터를 선언하거나 저장함
   */
  private Long submissionNo;
  private String submissionPri;
  private String submissionSection;
  /**
   * 리스트에서 호출될 때는 '0,1,2'로 값을 불러와 결재리스트 구분에 사용 됨
   * 상세에서 호출될 때는 '결재, 대기, 반려'로 값을 불러와 결재 상태로서 사용 됨
   */
  private String submissionStatus;
  /**
   * 결재가 가능한지 아닌지를 구분하기 위한 변수
   * !!!데이터베이스 컬럼에는 없고, 쿼리에서 생성한 컬럼!!!
   */
  private String approvalAble;
  private Long proposerEmployeeNo;
  private Long approverEmployeeNo;
  private Long annualLeaveNo;
  private String annualLeaveSection;
  private String annualLeavePjt;
  private String annualLeaveStart;
  private String annualLeaveEnd;
  private Integer annualLeaveTime;
  private String annualLeaveContent;
  /**
   * 결재 반려를 시킬 때 기안자에게 확인 요청을 주기위한 기안자의 값이 담긴 PK값
   * 결재 반려외에 결재 완료에서 1회 사용됨
   * 해당 결재건을 미열람으로 변경하여 기안자로 접근시 해당 건을 구분함
   */
  private Long rejectValue;

  public AnnualLeaveApprovalDetailForm(Long submissionNo, String submissionPri, String approvalAble, Long proposerEmployeeNo, Long approverEmployeeNo, Long annualLeaveNo, String annualLeaveSection, String annualLeavePjt, String annualLeaveStart, String annualLeaveEnd, Integer annualLeaveTime, String annualLeaveContent) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.approvalAble = approvalAble;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.annualLeaveNo = annualLeaveNo;
    this.annualLeaveSection = annualLeaveSection;
    this.annualLeavePjt = annualLeavePjt;
    this.annualLeaveStart = annualLeaveStart;
    this.annualLeaveEnd = annualLeaveEnd;
    this.annualLeaveTime = annualLeaveTime;
    this.annualLeaveContent = annualLeaveContent;
  }

  public AnnualLeaveApprovalDetailForm() {
  }
}
