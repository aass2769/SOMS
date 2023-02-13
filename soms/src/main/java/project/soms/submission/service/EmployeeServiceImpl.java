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
public class EmployeeServiceImpl implements EmployeeService{

  private final EmployeeRepository employeeRepository;

  @Override
  public ProposerDto proposer(Long employeeNo) {
    return employeeRepository.proposer(employeeNo);
  }

  @Override
  public List<ApproverDto> expenseApprover(Long employeeNo) {
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);

    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposerDto);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    return approverDto;
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
