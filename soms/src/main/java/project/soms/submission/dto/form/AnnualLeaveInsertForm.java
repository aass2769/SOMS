package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//연차신청서 상신시 데이터 검증을 위한 form클래스
public class AnnualLeaveInsertForm {

  @NotEmpty
  private String annualLeaveSection;
  @NotEmpty
  private String annualLeavePjt;
  @NotEmpty
  private String annualLeaveStart;
  @NotEmpty
  private String annualLeaveEnd;
  //1일 기준근로 시간 0~8을 적용하여 범위 설정
  @NotNull
  @Range(min = 0, max = 8)
  private Integer annualLeaveTime;
  @NotEmpty
  private String annualLeaveContent;

  public AnnualLeaveInsertForm() {
  }
}
