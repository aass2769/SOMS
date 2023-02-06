package project.soms.submission.service;

import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

public interface EmployeeService {

  ProposerDto proposer(Long employeeNo);

  List<ApproverDto> expenseApprover(Long employeeNo);

  List<ApproverDto> overtimeApprover(Long employeeNo);

  List<ApproverDto> annualLeaveApprover(Long employeeNo);

  List<ApproverDto> businessTripApprover(Long employeeNo);

  List<ApproverDto> incidentApprover(Long employeeNo);


}
