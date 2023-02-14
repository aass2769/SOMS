package project.soms.submission.repository;

import project.soms.submission.dto.SubmissionDto;

import java.util.List;

public interface ApprovalListRepository {

  List<SubmissionDto> underApprovalList(Long employeeNo, String submissionSection, String submissionDatetime);

}
