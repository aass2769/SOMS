package project.soms.submission.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ExpenseInsertForm {

  @NotEmpty
  private String expenseSection;
  @NotEmpty
  private String expensePjt;
  @NotEmpty
  private String expenseDate;
  @NotNull
  private Integer expenseCost;
  @NotEmpty
  private String expenseContent;

}
