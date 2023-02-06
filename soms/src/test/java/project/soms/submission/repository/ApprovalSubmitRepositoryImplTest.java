package project.soms.submission.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import project.soms.submission.dto.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class ApprovalSubmitRepositoryImplTest {

  private final ApprovalSubmitRepository approvalSubmitRepository;

  @Autowired
  public ApprovalSubmitRepositoryImplTest(ApprovalSubmitRepository approvalSubmitRepository) {
    this.approvalSubmitRepository = approvalSubmitRepository;
  }

  @Test
  void expenseSubmit() {
    //given 지출결의서 정보 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "20230201", 10000, "출장중 숙박비 결재 건");
    //when 지출결의서 정보 저장
    approvalSubmitRepository.expenseSubmit(expenseDto);
    //then 쿼리 성공 검증
    assertThat(approvalSubmitRepository.expenseSubmit(expenseDto)).isEqualTo(1);
  }

  @Test
  void expenseSubmitDateFail() {
    //given 날짜 정보 fail 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "날짜 fail", 10000, "날짜 fail test");
    //when 해당 값을 저장 시도 then 날짜 값 불일치 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void expenseSubmitSectionFail() {
    //given 구분 정보 null 할당
    ExpenseDto expenseDto = new ExpenseDto(null, "20230201", 10000, "구분 fail test");
    //when 해당 값을 저장 시도 then 구분 값 null 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void expenseSubmitCostFail() {
    //given 금랙 장보 null 할당
    ExpenseDto expenseDto = new ExpenseDto("법인카드", "20230201", null, "구분 fail test");
    //when 해당 값을 저장 시도 then 금액 null 확인
    assertThatThrownBy(() -> approvalSubmitRepository.expenseSubmit(expenseDto)).isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void overtimeSubmit() {
    //given 연장근로 입력 테스트
    OvertimeDto overtimeDto = new OvertimeDto("연장 근로", "20230201", 18, "20230201", 22, "야간 당직");
    //when
    approvalSubmitRepository.overtimeSubmit(overtimeDto);
    //then
    assertThat(approvalSubmitRepository.overtimeSubmit(overtimeDto)).isEqualTo(1);
  }

  @Test
  void annualLeaveSubmit() {
    //given 연차 정보 입력 테스트
    AnnualLeaveDto annualLeaveDto = new AnnualLeaveDto("연차", "20230201", "20230202", 0, "개인 관공서 업무로 인한 연차 사용");
    //when
    approvalSubmitRepository.annualLeaveSubmit(annualLeaveDto);
    //then
    assertThat(approvalSubmitRepository.annualLeaveSubmit(annualLeaveDto)).isEqualTo(1);
  }

  @Test
  void businessTripSubmit() {
    //given 출장 정보 입력 테스트
    BusinessTripDto businessTripDto = new BusinessTripDto("국내 출장", "20230201", "20230202", 0, "충주 현대모비스", "고객사 불합리 대응 4차입니다. 구매팀의 해결 요청드립니다.");
    //when
    approvalSubmitRepository.businessTripSubmit(businessTripDto);
    //then
    assertThat(approvalSubmitRepository.businessTripSubmit(businessTripDto)).isEqualTo(1);
  }

  @Test
  void incidentSubmit() {
    //given 시말서 정보 입력 테스트
    IncidentDto incidentDto = new IncidentDto("피손보고서", "사내 서버 pc 관리 부실로 인한 하드웨어 파손. 현재 사용 불가로 인한 재구입 필요");
    //when
    approvalSubmitRepository.incidentSubmit(incidentDto);
    //then
    assertThat(approvalSubmitRepository.incidentSubmit(incidentDto)).isEqualTo(1);
  }

}