package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.repository.mapper.EmployeeMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository{

  private final EmployeeMapper employeeMapper;


  @Override
  public ProposerDto proposer(Long employeeNo) {
    return employeeMapper.proposer(employeeNo);
  }

  @Override
  public List<ApproverDto> expenseApprover(ProposerDto proposerDto) {
    return employeeMapper.expenseApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> overtimeApprover(ProposerDto proposerDto) {
    return employeeMapper.overtimeApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> annualLeaveApprover(ProposerDto proposerDto) {
    return employeeMapper.annualLeaveApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> businessTripApprover(ProposerDto proposerDto) {
    return employeeMapper.businessTripApprover(proposerDto);
  }

  @Override
  public List<ApproverDto> incidentApprover(ProposerDto proposerDto) {
    return employeeMapper.incidentApprover(proposerDto);
  }
}
