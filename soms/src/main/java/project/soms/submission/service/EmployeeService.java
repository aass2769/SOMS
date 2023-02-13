package project.soms.submission.service;

import org.springframework.validation.BindingResult;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EmployeeService {

  ProposerDto proposer(Long employeeNo);

  List<ApproverDto> expenseApprover(Long employeeNo);

  List<ApproverDto> overtimeApprover(Long employeeNo);

  List<ApproverDto> annualLeaveApprover(Long employeeNo);

  List<ApproverDto> businessTripApprover(Long employeeNo);

  List<ApproverDto> incidentApprover(Long employeeNo);

  List<ApproverDto> approverCheck(BindingResult result, HttpServletRequest request, Long employeeNo);


}
