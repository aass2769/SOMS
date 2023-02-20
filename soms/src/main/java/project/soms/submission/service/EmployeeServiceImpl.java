package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.repository.EmployeeRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//기안자 정보와 결재자 리스트를 조회하는 기능을 담은 service impl클래스
public class EmployeeServiceImpl implements EmployeeService{

  /**
   * 기안자 정보와 결재자 리스트를 조회하는 기능을 담은 repository인터페이스
   */
  private final EmployeeRepository employeeRepository;

  //기안자 정보 조회
  @Override
  public ProposerDto proposer(Long employeeNo) {
    return employeeRepository.proposer(employeeNo);
  }

  //지출결의서 결재자 정보 조회 및 리스트 생성
  @Override
  public List<ApproverDto> expenseApprover(Long employeeNo) {

    //결재자 리스트 생성
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    //리스트에 결재 구분을 '결재'로 default 설정
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    return approverDto;
  }

  //연장근로신청서 결재자 정보 조회 및 리스트 생성
  @Override
  public List<ApproverDto> overtimeApprover(Long employeeNo) {

    //결재자 리스트 생성
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    //리스트에 결재 구분을 '결재'로 default 설정
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }

    return employeeRepository.overtimeApprover(proposerDto);
  }

  //연차신청서 결재자 정보 조회 및 리스트 생성
  @Override
  public List<ApproverDto> annualLeaveApprover(Long employeeNo) {

    //결재자 리스트 생성
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    //리스트에 결재 구분을 '결재'로 default 설정
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    return employeeRepository.annualLeaveApprover(proposerDto);
  }

  //출장신청서 결재자 정보 조회 및 리스트 생성
  @Override
  public List<ApproverDto> businessTripApprover(Long employeeNo) {

    //결재자 리스트 생성
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    //리스트에 결재 구분을 '결재'로 default 설정
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    return employeeRepository.businessTripApprover(proposerDto);
  }

  //시말서 결재자 정보 조회 및 리스트 생성
  @Override
  public List<ApproverDto> incidentApprover(Long employeeNo) {

    //결재자 리스트 생성
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    //리스트에 결재 구분을 '결재'로 default 설정
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    return employeeRepository.incidentApprover(proposerDto);
  }

  //기안자와 결재자의 관계와 결재자의 순서가 맞는지 확인
  @Override
  public List<ApproverDto> approverCheck(BindingResult result, HttpServletRequest request, Long employeeNo) {
    //결재자 정보 배열 생성
    List<ApproverDto> approverDto = new ArrayList<>();

    //결재자 사번과 결재구분 배열에 값을 추가
    for (int i = 0; i < 8; i++) {
      if (request.getParameter("approverNo"+i) != null && request.getParameter("approverNo" + i) != ""){
        approverDto.add(new ApproverDto(
            Long.parseLong(request.getParameter("approverNo" + i)),
            request.getParameter("approverName" + i),
            request.getParameter("submissionSection" + i)));
      }
    }
    //결재자가 없으면 오류에 추가
    if (approverDto.size() == 0) {
      result.reject("approverIsNull", new Object[]{}, null);
    }
    //결재자 선택에 중복이 있으면 오류 추가
    boolean check = false;
    for (ApproverDto i : approverDto) {
      for (ApproverDto j : approverDto) {
        ProposerDto approverCheck1 = proposer(i.getEmployeeNo());
        ProposerDto approverCheck2 = proposer(j.getEmployeeNo());
        if (approverCheck1.getEmployeeNo().equals(approverCheck2.getEmployeeNo()) && i != j) {
          result.reject("approverIsDuplocation", new Object[]{}, null);
          check = true;
          break;
        }
      }
      if (check) break;
    }
    //결재자 관계 선택이 올바르지 않거나 결재라인에 본인이 포함되면 오류 추가
    for (int i = 1; i < approverDto.size(); i++) {
      ProposerDto approverCheck1 = proposer(approverDto.get(i - 1).getEmployeeNo());
      ProposerDto approverCheck2 = proposer(approverDto.get(i).getEmployeeNo());
      ProposerDto proposer = proposer(employeeNo);
      if (proposer.getEmployeeNo().equals(approverCheck1.getEmployeeNo()) || proposer.getEmployeeNo().equals(approverCheck2.getEmployeeNo())) {
        result.reject("approverIsNotProposer", new Object[]{}, null);
      } else if (approverCheck1.getManageNo() > approverCheck2.getManageNo()) {
        result.reject("approverIsMiss", new Object[]{}, null);
      }
    }
    return approverDto;
  }
}
