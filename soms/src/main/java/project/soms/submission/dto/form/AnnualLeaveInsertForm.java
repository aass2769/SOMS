package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnnualLeaveInsertForm {

  @NotEmpty
  private String annualLeaveSection;
  @NotEmpty
  private String annualLeavePjt;
  @NotEmpty
  private String annualLeaveStart;
  @NotEmpty
  private String annualLeaveEnd;
  @NotNull
  @Range(min = 0, max = 8)
  private Integer annualLeaveTime;
  @NotEmpty
  private String annualLeaveContent;

  public AnnualLeaveInsertForm() {
  }
}
