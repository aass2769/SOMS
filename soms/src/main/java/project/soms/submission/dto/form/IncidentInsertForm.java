package project.soms.submission.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class IncidentInsertForm {

  @NotEmpty
  private String incidentSection;
  @NotEmpty
  private String incidentPjt;
  @NotEmpty
  private String incidentContent;

}
