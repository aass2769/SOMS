package project.soms.submission.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class EmployeeServiceImplTest {

  private final EmployeeService employeeService;

  @Autowired
  public EmployeeServiceImplTest(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Test
  void proposer() {
    //given 기안자 사번 입력
    Long employeeNo = 20230201011L;
    //when 기안자 정보 조회
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //then 입력한 사번에 맞는 데이터가 있는지 확인
    assertThat(proposerDto.getEmployeeNo()).isEqualTo(20230201011L);
  }

  @Test
  void proposerFail() {
    //given 기안자 사번 입력
    Long employeeNo = 1111L;
    //when 기안자 정보 조회
    ProposerDto result = employeeService.proposer(employeeNo);
    //then 입력한 사번에 맞는 데이터가 있는지 확인
    assertThatThrownBy(() -> result.getEmployeeNo()).isInstanceOf(NullPointerException.class);
  }

  @Test
  void expenseApprover() {
    //given 기안자 사번 입력 , 기안자 정보 조회
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when 기안자 정보로 결재자 배열 생성
    List<ApproverDto> approverList = employeeService.expenseApprover(proposerDto.getEmployeeNo());
    //then 배열 크기 및 기안자와 결재자 간 관계 검증
    assertThat(approverList.size()).isEqualTo(5);
    assertThat(approverList.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
    //다른 크기의 실패 상황 검증
    assertThat(approverList.size()).isNotEqualTo(4);
  }

  @Test
  void expenseApproverFail() {
    //given 기안자 fail 사번 입력 , 기안자 정보 조회
    Long employeeNo = 1111L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when 기안자 정보로 결재자 배열 생성 then 생성 불가 검증
    assertThatThrownBy(() -> employeeService.expenseApprover(proposerDto.getEmployeeNo())).isInstanceOf(NullPointerException.class);
  }

  @Test
  void overtimeApprover() {
    //given
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when
    List<ApproverDto> approverList = employeeService.overtimeApprover(proposerDto.getEmployeeNo());
    //then
    assertThat(approverList.size()).isEqualTo(2);
    assertThat(approverList.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
  }

  @Test
  void annualLeaveApprover() {
    //given
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when
    List<ApproverDto> approverList = employeeService.annualLeaveApprover(proposerDto.getEmployeeNo());
    //then
    assertThat(approverList.size()).isEqualTo(2);
    assertThat(approverList.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
  }

  @Test
  void businessTripApprover() {
    //given
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when
    List<ApproverDto> approverList = employeeService.businessTripApprover(proposerDto.getEmployeeNo());
    //then
    assertThat(approverList.size()).isEqualTo(4);
    assertThat(approverList.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
  }

  @Test
  void incidentApprover() {
    //given
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeService.proposer(employeeNo);
    //when
    List<ApproverDto> approverList = employeeService.incidentApprover(proposerDto.getEmployeeNo());
    //then
    assertThat(approverList.size()).isEqualTo(5);
    assertThat(approverList.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
  }



}