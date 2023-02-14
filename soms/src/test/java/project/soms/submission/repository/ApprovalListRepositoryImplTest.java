package project.soms.submission.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.soms.submission.dto.ApproverDto;
import project.soms.submission.dto.ProposerDto;
import project.soms.submission.dto.SubmissionDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ApprovalListRepositoryImplTest {

  private final ApprovalListRepository approvalListRepository;
  private final EmployeeRepository employeeRepository;

  @Autowired
  public ApprovalListRepositoryImplTest(ApprovalListRepository approvalListRepository, EmployeeRepository employeeRepository) {
    this.approvalListRepository = approvalListRepository;
    this.employeeRepository = employeeRepository;
  }


  @Test
  void underApprovalList() {
    //given
    Long employeeNo = 20230201009L;
    String submissionSection = null;
    String submissionDatetime = null;
    //when
    List<SubmissionDto> underApprovalList = approvalListRepository.underApprovalList(employeeNo, submissionSection, submissionDatetime);
    log.info("underApprovalList={}", underApprovalList);
    //then
    assertThat(underApprovalList.get(0).getApproverEmployeeNo()).isEqualTo(employeeNo);
  }

  @Test
  void underApprovalListFail() {
    //given
    Long employeeNo = 20230201009L;
    ProposerDto proposer = employeeRepository.proposer(employeeNo);
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposer);
    String submissionSection = null;
    String submissionDatetime = null;
    //when
    List<SubmissionDto> approvalList = approvalListRepository.underApprovalList(approverDto.get(0).getEmployeeNo(), submissionSection, submissionDatetime);
    log.error("approvalList={}", approvalList);
    //then
    assertThat(approvalList.get(0).getApproverEmployeeNo()).isNotEqualTo(employeeNo);
  }
}