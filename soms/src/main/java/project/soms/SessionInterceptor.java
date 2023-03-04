package project.soms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.repository.mapper.ApprovalListMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

  private final ApprovalListMapper approvalListMapper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    if (session.getAttribute("LOGIN_EMPLOYEE") == null) {
      response.sendRedirect("/login");
      return false;
    } else {

      //값이 있을 땐 객체에 세션 값을 저장
      EmployeeDto employeeDto = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");

      //결재 내역 리스트 생성
      List<SubmissionDto> approvalList = approvalListMapper.approvalList(employeeDto.getEmployeeNo(), "", "");

      //미확인 건을 구분하여 저장할 변수 선언
      Integer under = 0; Integer complete = 0; Integer reject = 0; Integer total;

      for (SubmissionDto approval : approvalList) {
        //미확인 상태인 '결재 중 내역' 카운트
        if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("1") &&
            (approval.getProposerShowable().equals("가능") || approval.getApproverShowable().equals("가능")) &&
            approval.getSubmissionOpen().equals("미열람")) {
          under += 1;
        }
        //미확인 상태인 '결재 완료 내역' 카운트
        if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("2") &&
            ((!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null &&
                approval.getApproverEmployeeNo() == null) || (!approval.getApproverShowable().equals("불가") &&
                approval.getApproverEmployeeNo() != null)) && (approval.getSubmissionOpen().equals("본인건") &&
                approval.getApproverEmployeeNo() == null)) {
          complete += 1;
        }
        //미확인 상태인 '결재 반려 내역' 카운트
        if (approval.getSubmissionNo() != null && approval.getSubmissionStatus().equals("0") &&
            ((!approval.getProposerShowable().equals("불가") && approval.getProposerEmployeeNo() != null &&
                approval.getApproverEmployeeNo() == null) || (!approval.getApproverShowable().equals("불가") &&
                approval.getApproverEmployeeNo() != null)) && (approval.getSubmissionOpen().equals("본인건") &&
                approval.getApproverEmployeeNo() == null)) {
          reject += 1;
        }
      }
      //미확인 상태의 결재내역 합산
      total = under + complete + reject;
      //해당 값들 세션에 저장
      session.setAttribute("underApproval", under);
      session.setAttribute("completeApproval", complete);
      session.setAttribute("rejectApproval", reject);
      session.setAttribute("totalApproval", total);
    }
    return true;
  }
}
