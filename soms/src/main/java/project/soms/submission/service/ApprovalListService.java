package project.soms.submission.service;

import project.soms.submission.dto.SubmissionDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ApprovalListService {

  List<SubmissionDto> underApprovalList(Long employeeNo, HttpServletRequest request);
}
