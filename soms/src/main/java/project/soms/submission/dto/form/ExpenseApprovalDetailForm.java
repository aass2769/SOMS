package project.soms.submission.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseApprovalDetailForm {

  private Long submissionNo;
  private String submissionPri;
  private String submissionSection;
  private String submissionStatus;
  private String approvalAble;
  private Long proposerEmployeeNo;
  private Long approverEmployeeNo;
  private Long expenseNo;
  private String expenseSection;
  private String expensePjt;
  private String expenseDate;
  private Integer expenseCost;
  private String expenseContent;

  private Long rejectValue;

  public ExpenseApprovalDetailForm(Long submissionNo, String submissionPri, String approvalAble, Long proposerEmployeeNo, Long approverEmployeeNo, Long expenseNo, String expenseSection, String expensePjt, String expenseDate, Integer expenseCost, String expenseContent) {
    this.submissionNo = submissionNo;
    this.submissionPri = submissionPri;
    this.approvalAble = approvalAble;
    this.proposerEmployeeNo = proposerEmployeeNo;
    this.approverEmployeeNo = approverEmployeeNo;
    this.expenseNo = expenseNo;
    this.expenseSection = expenseSection;
    this.expensePjt = expensePjt;
    this.expenseDate = expenseDate;
    this.expenseCost = expenseCost;
    this.expenseContent = expenseContent;
  }
}
