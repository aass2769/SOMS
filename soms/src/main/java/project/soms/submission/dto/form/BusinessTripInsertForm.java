package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//출장신청서 상신시 데이터 검증을 위한 form클래스
public class BusinessTripInsertForm {

  @NotEmpty
  private String businessTripSection;
  @NotEmpty
  private String businessTripPjt;
  @NotEmpty
  private String businessTripStart;
  @NotEmpty
  private String businessTripEnd;
  //1일 최대 외근 근로 시간 0~8을 적용하여 범위 설정
  @NotNull
  @Range(min = 0, max = 8)
  private Integer businessTripTime;
  @NotEmpty
  private String businessTripDestination;
  @NotEmpty
  private String businessTripContent;

}
