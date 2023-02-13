package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OvertimeInsertForm {
  @NotEmpty
  private String overtimeSection;
  @NotEmpty
  private String overtimePjt;
  @NotEmpty
  private String overtimeStartDate;
  @NotNull
  @Range(min = 0, max = 23)
  private Integer overtimeStartTime;
  @NotEmpty
  private String overtimeEndDate;
  @NotNull
  @Range(min = 0, max = 23)
  private Integer overtimeEndTime;
  @NotEmpty
  private String overtimeContent;
}
