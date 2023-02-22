package project.soms.submission.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ApprovalSubmitRepositoryImplTest {

  private final ApprovalSubmitRepository approvalSubmitRepository;

  @Autowired
  public ApprovalSubmitRepositoryImplTest(ApprovalSubmitRepository approvalSubmitRepository) {
    this.approvalSubmitRepository = approvalSubmitRepository;
  }

  /*@Test
  void expenseSubmit() {
    //given 지출결의서 정보 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "유지보수", "20230201", 10000, "출장중 숙박비 결재 건");
    //when 지출결의서 정보 저장
    approvalSubmitRepository.expenseSubmit(expenseDto);
    //then 쿼리 성공 검증
    assertThat(approvalSubmitRepository.expenseSubmit(expenseDto)).isEqualTo(1);
  }

  @Test
  void expenseSubmitDateFail() {
    //given 날짜 정보 fail 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "유지보수", "날짜 fail", 10000, "날짜 fail test");
    //when 해당 값을 저장 시도 then 날짜 값 불일치 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void expenseSubmitSectionFail() {
    //given 구분 정보 null 할당
    ExpenseDto expenseDto = new ExpenseDto(null, "유지보수", "20230201", 10000, "구분 fail test");
    //when 해당 값을 저장 시도 then 구분 값 null 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void expenseSubmitCostFail() {
    //given 금랙 장보 null 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "유지보수", "20230201", null, "구분 fail test");
    //when 해당 값을 저장 시도 then 금액 null 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }*/

}