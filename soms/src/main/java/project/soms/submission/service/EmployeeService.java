package project.soms.submission.service;

import org.springframework.validation.BindingResult;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//기안자 정보와 결재자 리스트를 조회하는 기능을 담은 service인터페이스
public interface EmployeeService {

  //기안자 정보 조회
  ProposerDto proposer(Long employeeNo);

  //지출결의서 결재자 리스트 자동 생성
  List<ApproverDto> expenseApprover(Long employeeNo);

  //연장근로신청서 결재자 리스트 자동 생성
  List<ApproverDto> overtimeApprover(Long employeeNo);

  //연차신청서 결재자 리스트 자동 생성
  List<ApproverDto> annualLeaveApprover(Long employeeNo);

  //출장신청서 결재자 리스트 자동 생성
  List<ApproverDto> businessTripApprover(Long employeeNo);

  //시말서 결재자 리스트 자동 생성
  List<ApproverDto> incidentApprover(Long employeeNo);

  //결재 서식에서 기안자가 입력한 결재자 정보가 맞는지 검증
  List<ApproverDto> approverCheck(BindingResult result, HttpServletRequest request, Long employeeNo);


}
