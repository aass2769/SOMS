package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import project.soms.submission.service.ApprovalSubmitService;

@Slf4j
@Controller
@RequiredArgsConstructor
//@RequestMapping("submission")
public class ApprovalSubmitController {

  private final ApprovalSubmitService approvalSubmitService;

//  @PostMapping("submission/form/expense")
//  public String submitv1(@Validated SubmissionInsertForm submissionDto, BindingResult submissionBindingresult,
//                         @Validated ExpenseInsertForm expenseDto, BindingResult expenseBindingresult,
//                         Long employeeNo, HttpServletRequest request, Model model, RedirectAttributes redirect) {
//
//    if (submissionBindingresult.hasErrors() || expenseBindingresult.hasErrors()) {
//      log.info("errors={}", submissionBindingresult.getFieldErrors());
//      log.info("errors={}", expenseBindingresult.getFieldErrors());
//
//      return "submission/submitForm/expense";
//    }
//
//    ExpenseDto expenseDtos = new ExpenseDto(expenseDto.getExpenseSection(), expenseDto.getExpenseDate(),
//        expenseDto.getExpenseCost(), expenseDto.getExpenseContent());
//    SubmissionDto submissionDtos = new SubmissionDto(
//        submissionDto.getSubmissionPri(), submissionDto.getSubmissionDatetime(), submissionDto.getSubmissionPjt());
//    List<Long> approverDto = new ArrayList<>();
//    List<String> submissionSection = new ArrayList<>();
//    for (int i = 0; i < 8; i++) {
//      if (request.getParameter("approver"+i) != null && request.getParameter("approver" + i) != ""){
//        approverDto.add(Long.parseLong(request.getParameter("approver" + i)));
//        submissionSection.add(request.getParameter("submissionSection" + i));
//      }
//    }
//
//    approvalSubmitService.expenseSubmit(submissionDtos, expenseDtos, employeeNo, approverDto, submissionSection);
//    return "submission/approvalList/underApproval";
//  }

}
