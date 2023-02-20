package project.soms.submission.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
//시말서 상신시 데이터 검증을 위한 form클래스
public class IncidentInsertForm {

  @NotEmpty
  private String incidentSection;
  @NotEmpty
  private String incidentPjt;
  @NotEmpty
  private String incidentContent;

}
