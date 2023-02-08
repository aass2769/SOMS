package project.soms.submission.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SubmissionInsertForm {

  @NotEmpty
  private String submissionPri;
  @NotEmpty
  private String submissionDatetime;
  @NotEmpty
  private String submissionPjt;

}
