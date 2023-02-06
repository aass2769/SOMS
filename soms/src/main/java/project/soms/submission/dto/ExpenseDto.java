package project.soms.submission.dto;

import lombok.Data;

@Data
public class ExpenseDto {

  private Long expenseNo;
  private String expenseSection;
  private String expenseDate;
  private Integer expenseCost;
  private String expenseContent;

  public ExpenseDto(String expenseSection, String expenseDate, Integer expenseCost, String expenseContent) {
    this.expenseSection = expenseSection;
    this.expenseDate = expenseDate;
    this.expenseCost = expenseCost;
    this.expenseContent = expenseContent;
  }
}
