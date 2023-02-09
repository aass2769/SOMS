package project.soms.submission.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ExpenseDto;
import project.soms.submission.dto.SubmissionDto;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
class ApprovalSubmitServiceImplTest {

  private final ApprovalSubmitService approvalSubmitService;
  private final EmployeeService employeeService;

  @Autowired
  public ApprovalSubmitServiceImplTest(ApprovalSubmitService approvalSubmitService, EmployeeService employeeService) {
    this.approvalSubmitService = approvalSubmitService;
    this.employeeService = employeeService;
  }

  @BeforeEach
  public void beforeEach() {
    log.info("테스트 시작");
  }

  @AfterEach
  public void afterEach() {
    log.info("테스트 완료");
  }

  @Test
  void expenseSubmit() {
    //given 지출결의서와 결재서식에 값 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "유지보수", "20230201", 10000, "출장중 숙박비 결재 건");
    SubmissionDto submissionDto = new SubmissionDto("1234", "2023-02-01 11:11:11", "대기", "미열람");
    Long employeeNo = 20230201011L;
    List<ApproverDto> approverDto = employeeService.expenseApprover(employeeNo);
    for (ApproverDto i : approverDto) {
      i.setSubmissionSection("결재");
    }
    //when 지출결의서, 결재서식 값 저장
    approvalSubmitService.expenseSubmit(submissionDto, expenseDto, employeeNo, approverDto);
    //then 입력한 값과 저장한 값이 일치하는지 검증
    assertThat(submissionDto.getExpenseNo()).isEqualTo(expenseDto.getExpenseNo());
  }

  @Test
  void expenseSubmitApproverFail() {
    //given 지출결의서와 결재서식에 값 할당 결재라인 실패값 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "유지보수","20230201", 10000, "출장중 숙박비 결재 건");
    SubmissionDto submissionDto = new SubmissionDto("1234", "2023-02-01 11:11:11", "대기", "미열람");
    Long employeeNo = 20230201011L;
    List<ApproverDto> approverDto = new ArrayList<>();
    approverDto.add(new ApproverDto(employeeNo,"이름","결재"));
    //when 지출결의서, 결재서식 값 저장 then insert 싶해 겁증
    assertThatThrownBy(() ->
        approvalSubmitService.expenseSubmit(submissionDto, expenseDto, employeeNo, approverDto))
        .isInstanceOf(IllegalArgumentException.class);
  }

}