package project.soms.submission.dto;

import lombok.Data;

@Data
//지출결의서 필드값
public class ExpenseDto {

  private Long expenseNo;
  private String expenseSection;
  private String expensePjt;
  private String expenseDate;
  private Integer expenseCost;
  private String expenseContent;

  public ExpenseDto(String expenseSection, String expensePjt, String expenseDate, Integer expenseCost, String expenseContent) {
    this.expenseSection = expenseSection;
    this.expensePjt = expensePjt;
    this.expenseDate = expenseDate;
    this.expenseCost = expenseCost;
    this.expenseContent = expenseContent;
  }

  public ExpenseDto() {
  }
}
