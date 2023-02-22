package project.soms.submission.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class SubmissionSubmitControllerTest {

  @Autowired
  private SubmissionSubmitController submissionSubmitController;
  @Mock
  private EmployeeService employeeService;

  /*@Test
  void employeeInfo() {
    //given parameter 설정을 위한 객체들 Mock으로 선언 후 사번 할당
    Model model = mock(Model.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);
    Long employeeNo = 20230201011L;
    //when 해당 사번으로 기안자 객체 생성 및 세션 반환 설정
    ProposerDto employee = new ProposerDto(employeeNo, "그린컴", "경영 지원-경영 관리", 1L, "인턴");
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("LOGIN_EMPLOYEE")).thenReturn(employee);
    HttpSession result = submissionSubmitController.employeeInfo(model, request);
    //then 세션 값으로 기안자 객체 생성 성공 검증
    assertThat(result.getAttribute("LOGIN_EMPLOYEE")).isEqualTo(employee);
  }

  @Test
  void employeeInfoFail() {
    //given parameter 설정을 위한 객체들 Mock으로 선언 후 사번 할당
    Model model = mock(Model.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpSession session = mock(HttpSession.class);
    Long employeeNo = 20230201011L;
    //when 해당 사번으로 기안자 객체 생성 및 세션 반환 설정, 세션에서에서 받게될 객체를 null로 설정
    ProposerDto employee = employeeService.proposer(employeeNo);
    when(employeeService.proposer(employeeNo)).thenReturn(null);
    when(request.getSession()).thenReturn(session);
    HttpSession result = submissionSubmitController.employeeInfo(model, request);
    ProposerDto actualEmployee = (ProposerDto) session.getAttribute("LOGIN_EMPLOYEE");
    //then 반환된 값이 null인지 검증
    assertNull(actualEmployee);
  }*/

  @Test
  public void getParameterApprovers() {
    //given parameter를 위한 객체 Mock으로 설정 후 파라미터 값 설정
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("approverNo0")).thenReturn("20230201009");
    when(request.getParameter("approverName0")).thenReturn("모주혜");
    when(request.getParameter("submissionSection0")).thenReturn("검토");
    when(request.getParameter("approverNo1")).thenReturn("20230201007");
    when(request.getParameter("approverName1")).thenReturn("정다영");
    when(request.getParameter("submissionSection1")).thenReturn("결재");
    when(request.getParameter("approverNo3")).thenReturn(null);
    when(request.getParameter("approverName3")).thenReturn(null);
    when(request.getParameter("submissionSection3")).thenReturn(null);
    //when 해당 파라미터로 결재자 리스트 반환
    List<ApproverDto> approverDtos = submissionSubmitController.getParameterApprovers(request);
    //then 결재자 리스트의 크기와 결재자 정보 일치 검증
    assertThat(approverDtos.size()).isEqualTo(2);
    assertThat(approverDtos.get(0)).isEqualTo(new ApproverDto(20230201009L, "모주혜", "검토"));
  }

  @Test
  public void getParameterApproversFail() {
    //given parameter를 위한 객체 Mock으로 설정 후 파라미터 값 설정. 사번에 할당될 수 없는 값 설정
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("approverNo0")).thenReturn("실패하는 사번");
    when(request.getParameter("approverName0")).thenReturn("모주혜");
    when(request.getParameter("submissionSection0")).thenReturn("검토");
    //when 해당값으로 리스트 생성 then 사번 설정 오류 검증
    assertThatThrownBy(() -> submissionSubmitController.getParameterApprovers(request)).isInstanceOf(NumberFormatException.class);
  }



}