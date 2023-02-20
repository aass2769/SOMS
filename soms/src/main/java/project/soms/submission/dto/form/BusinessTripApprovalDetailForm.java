package project.soms.submission.dto.form;

import lombok.Data;

@Data
//출장 서식을 조회할 때 사용되는 data클래스
public class BusinessTripApprovalDetailForm {

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

  private Long businessTripNo;
  private String businessTripSection;
  private String businessTripPjt;
  private String businessTripStart;
  private String businessTripEnd;
  private Integer businessTripTime;
  private String businessTripDestination;
  private String businessTripContent;
  /**
   * 결재 반려를 시킬 때 기안자에게 확인 요청을 주기위한 기안자의 값이 담긴 PK값
   * 결재 반려외에 결재 완료에서 1회 사용됨
   * 해당 결재건을 미열람으로 변경하여 기안자로 접근시 해당 건을 구분함
   */
  private Long rejectValue;

  public BusinessTripApprovalDetailForm(Long submissionNo, String submissionPri, String approvalAble, Long proposerEmployeeNo, Long approverEmployeeNo, Long businessTripNo, String businessTripSection, String businessTripPjt, String businessTripStart, String businessTripEnd, Integer businessTripTime, String businessTripDestination, String businessTripContent) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.approvalAble = approvalAble;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.businessTripNo = businessTripNo;
    this.businessTripSection = businessTripSection;
    this.businessTripPjt = businessTripPjt;
    this.businessTripStart = businessTripStart;
    this.businessTripEnd = businessTripEnd;
    this.businessTripTime = businessTripTime;
    this.businessTripDestination = businessTripDestination;
    this.businessTripContent = businessTripContent;
  }

  public BusinessTripApprovalDetailForm() {
  }
}
