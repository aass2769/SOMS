package project.soms.submission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.soms.submission.dto.ExpenseDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.service.ApprovalSubmitService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("submission/approval")
public class approvalSubmitController {

  private final ApprovalSubmitService approvalSubmitService;

  @PostMapping("expenseSubmit")
  public String submitv1(SubmissionDto submissionDto, ExpenseDto expenseDto, Long employeeNo, HttpServletRequest request) {

    List<Long> approverDto = new ArrayList<>();
    List<String> submissionSection = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      if (request.getParameter("approver"+i) != null && request.getParameter("approver" + i) != ""){
        approverDto.add(Long.parseLong(request.getParameter("approver" + i)));
        submissionSection.add(request.getParameter("submissionSection" + i));
      }
    }

    approvalSubmitService.expenseSubmit(submissionDto, expenseDto, employeeNo, approverDto, submissionSection);
    return "submission/approvalList/underApproval";
  }

}
