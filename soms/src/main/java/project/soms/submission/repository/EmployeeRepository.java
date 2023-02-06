package project.soms.submission.repository;

import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

@Repository
public interface EmployeeRepository {

  ProposerDto proposer(Long employeeNo);

  List<ApproverDto> expenseApprover(ProposerDto proposerDto);
  List<ApproverDto> overtimeApprover(ProposerDto proposerDto);
  List<ApproverDto> annualLeaveApprover(ProposerDto proposerDto);
  List<ApproverDto> businessTripApprover(ProposerDto proposerDto);
  List<ApproverDto> incidentApprover(ProposerDto proposerDto);

}
