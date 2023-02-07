package project.soms.submission.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class EmployeeRepositoryImplTest {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeRepositoryImplTest(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Test
  void proposer() {
    //given 기안자 사번 할당
    Long employeeNo = 20230201011L;
    //when 기안자 사번을 매개변수로 기안자 조회
    ProposerDto result = employeeRepository.proposer(employeeNo);
    //then 위의 메서드의 결과와 실제의 데이터를 비교 검증
    assertThat(result.getEmployeeNo()).isEqualTo(employeeNo);
  }

  @Test
  void proposerFail() {
    //given 기안자 fail 사번 할당
    Long employeeNo = 1111L;
    //when 기안자 사번을 매개변수로 기안자 조회
    ProposerDto result = employeeRepository.proposer(employeeNo);
    //then 조회에서 값 없음
    assertThatThrownBy(() -> result.getEmployeeNo()).isInstanceOf(NullPointerException.class);
  }

  @Test
  void expenseApprover() {
    //given 기안자 정보 할당
    Long employeeNo = 20230201011L;
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    //when 기안자의 정보를 기반으로 결재자 배열 생성
    List<ApproverDto> result = employeeRepository.expenseApprover(proposerDto);
    //then 결재자 배열의 크기 검증 및 결재자와 기안자의 관계 검증
    assertThat(result.get(0).getManageNo()).isGreaterThan(proposerDto.getManageNo());
  }

  @Test
  void expenseApproverFail() {
    //given 기안자 fail 사번 할당
    Long employeeNo = 1111L;
    ProposerDto proposerDto = employeeRepository.proposer(employeeNo);
    //when 결재자 정보 배열 생성 then 기안자 정보 null로 인한 쿼리 오류
    assertThatThrownBy(() -> employeeRepository.expenseApprover(proposerDto)).
        isInstanceOf(MyBatisSystemException.class);
  }

}