package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//연차신청서 상신시 데이터 검증을 위한 form클래스
public class OvertimeInsertForm {

  @NotEmpty
  private String overtimeSection;
  @NotEmpty
  private String overtimePjt;
  @NotEmpty
  private String overtimeStartDate;
  //0시부터 24시 사이에 값을 할당
  @NotNull
  @Range(min = 0, max = 23)
  private Integer overtimeStartTime;
  @NotEmpty
  private String overtimeEndDate;
  //0시부터 24시 사이에 값을 할당
  @NotNull
  @Range(min = 0, max = 23)
  private Integer overtimeEndTime;
  @NotEmpty
  private String overtimeContent;

}
