package project.soms.submission.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApprovalListServiceImplTest {

  private final ApprovalListService approvalListService;

  @Autowired
  public ApprovalListServiceImplTest(ApprovalListService approvalListService) {
    this.approvalListService = approvalListService;
  }

  @Test
  void underApprovalList() {
  }


}