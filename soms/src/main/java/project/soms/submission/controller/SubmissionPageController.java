package project.soms.submission.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("submission")
public class SubmissionPageController {

  @GetMapping("expense")
  public String expenseForm() {
    return "submission/submitForm/expense";
  }

  @GetMapping("overtime")
  public String overtimeForm() {
    return "submission/submitForm/overtime";
  }

  @GetMapping("annualLeave")
  public String annualLeaveForm() {
    return "submission/submitForm/annualLeave";
  }

  @GetMapping("businessTrip")
  public String businessTripForm() {
    return "submission/submitForm/businessTrip";
  }

  @GetMapping("incident")
  public String incidentForm() {
    return "submission/submitForm/incident";
  }

  @GetMapping("underApproval")
  public String underApproval() {
    return "submission/approvalList/underApproval";
  }

  @GetMapping("completeApproval")
  public String completeApproval() {
    return "submission/approvalList/completeApproval";
  }

  @GetMapping("rejectedApproval")
  public String rejectedApproval() {
    return "submission/approvalList/rejectedApproval";
  }

}
