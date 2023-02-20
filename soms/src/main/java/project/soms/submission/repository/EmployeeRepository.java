package project.soms.submission.repository;

import org.springframework.stereotype.Repository;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

@Repository
//기안자 정보 조회 및 결재자 자동완성 값을 담은 repository인터페이스
public interface EmployeeRepository {

  //기안자 정보 조회
  ProposerDto proposer(Long employeeNo);

  //지출결의서 결재자 자동 완성
  List<ApproverDto> expenseApprover(ProposerDto proposerDto);
  //연장근로신청서 결재자 자동 완성
  List<ApproverDto> overtimeApprover(ProposerDto proposerDto);
  //연차신청서 결재자 자동 완성
  List<ApproverDto> annualLeaveApprover(ProposerDto proposerDto);
  //출장신청서 결재자 자동 완성
  List<ApproverDto> businessTripApprover(ProposerDto proposerDto);
  //시말서 결재자 자동 완성
  List<ApproverDto> incidentApprover(ProposerDto proposerDto);

}
