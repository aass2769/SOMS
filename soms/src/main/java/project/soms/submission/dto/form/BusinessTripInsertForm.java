package project.soms.submission.dto.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BusinessTripInsertForm {

  @NotEmpty
  private String businessTripSection;
  @NotEmpty
  private String businessTripPjt;
  @NotEmpty
  private String businessTripStart;
  @NotEmpty
  private String businessTripEnd;
  @NotNull
  @Range(min = 0, max = 8)
  private Integer businessTripTime;
  @NotEmpty
  private String businessTripDestination;
  @NotEmpty
  private String businessTripContent;

}
