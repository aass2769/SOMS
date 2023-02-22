package project.soms.submission.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


  /*@Test
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
  }*/

  /*@Test
  void underApprovalListFail() {
    //given
    Long employeeNo = 20230201009L;
    ProposerDto proposer = employeeRepository.proposer(employeeNo);
    List<ApproverDto> approverDto = employeeRepository.expenseApprover(proposer);
    String submissionSection = null;
    String submissionDatetime = null;
    //when
    List<SubmissionDto> approvalList = approvalListRepository.underApprovalList(approverDto.get(0).getEmployeeNo(), submissionSection, submissionDatetime);
    //then
    assertThat(approvalList.get(0).getApproverEmployeeNo()).isNotEqualTo(employeeNo);
  }*/
}