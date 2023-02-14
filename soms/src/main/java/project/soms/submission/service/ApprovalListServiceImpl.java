package project.soms.submission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.soms.submission.dto.SubmissionDto;
import project.soms.submission.repository.ApprovalListRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalListServiceImpl implements ApprovalListService{

  private final ApprovalListRepository approvalListRepository;

  @Override
  public List<SubmissionDto> underApprovalList(Long employeeNo, HttpServletRequest request) {

    //검색 조건이 있는지 확인 후 조건이 있으면 값을 할당 없으면 공백을 할당하여 전체 결과가 노오도록
    String submissionSection = "";
    String submissionDatetime = "";
    if (request.getParameter("submissionSection") != null) {
      submissionSection = request.getParameter("submissionSection");
    }
    if (request.getParameter("submissionDatetime") != null && request.getParameter("submissionDatetime") != "") {
      submissionDatetime = request.getParameter("submissionDatetime");
    }

    return approvalListRepository.underApprovalList(employeeNo, submissionSection, submissionDatetime);
  }
}
