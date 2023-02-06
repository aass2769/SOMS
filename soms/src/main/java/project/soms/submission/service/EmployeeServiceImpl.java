package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

  private final EmployeeRepository employeeRepository;

  @Override
  public ProposerDto proposer(Long employeeNo) {
    return employeeRepository.proposer(employeeNo);
  }

  @Override
  public List<ApproverDto> expenseApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    return employeeRepository.expenseApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> overtimeApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    return employeeRepository.overtimeApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> annualLeaveApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    return employeeRepository.annualLeaveApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> businessTripApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    return employeeRepository.businessTripApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> incidentApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    return employeeRepository.incidentApprover(proposerDto);
  }
}
