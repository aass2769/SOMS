package project.soms.submission.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
//지출결의서 상신시 데이터 검증을 위한 form클래스
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
