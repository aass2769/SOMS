package project.soms.submission.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.repository.mapper.EmployeeMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
//기안자 정보 조회 및 결재자 자동완성 값을 담은 repository impl클래스
public class EmployeeRepositoryImpl implements EmployeeRepository{

  //기안자 정보 조회 및 결재자 자동완성 쿼리를 담은 mapper인터페이스
  private final EmployeeMapper employeeMapper;

  //기안자 정보 조회
  @Override
  public ProposerDto proposer(Long employeeNo) {
    return employeeMapper.proposer(employeeNo);
  }

  //지출결의서 결재자 자동 완성 리스트 (기안자 기준으로 조회)
  @Override
  public List<ApproverDto> expenseApprover(ProposerDto proposerDto) {
    return employeeMapper.expenseApprover(proposerDto);
  }

  //연장근로신청서 결재자 자동 완성 리스트 (기안자 기준으로 조회)
  @Override
  public List<ApproverDto> overtimeApprover(ProposerDto proposerDto) {
    return employeeMapper.overtimeApprover(proposerDto);
  }

  //연차신청서 결재자 자동 완성 리스트 (기안자 기준으로 조회)
  @Override
  public List<ApproverDto> annualLeaveApprover(ProposerDto proposerDto) {
    return employeeMapper.annualLeaveApprover(proposerDto);
  }

  //출장신청서 결재자 자동 완성 리스트 (기안자 기준으로 조회)
  @Override
  public List<ApproverDto> businessTripApprover(ProposerDto proposerDto) {
    return employeeMapper.businessTripApprover(proposerDto);
  }

  //시말서 결재자 자동 완성 리스트 (기안자 기준으로 조회)
  @Override
  public List<ApproverDto> incidentApprover(ProposerDto proposerDto) {
    return employeeMapper.incidentApprover(proposerDto);
  }
}
