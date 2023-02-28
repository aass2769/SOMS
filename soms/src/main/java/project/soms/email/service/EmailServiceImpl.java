package project.soms.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.soms.email.dto.EmailDto;
import project.soms.email.repository.EmailRepository;
import project.soms.employee.dto.EmployeeDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final EmailRepository emailRepository;
  private static final String emailPw = "Soms2023!";

  @Override
  public List<EmailDto> emailList(HttpServletRequest request) {

    String folderName = request.getParameter("folderName");
    return emailRepository.emailList(getEmployee(request).getEmployeeId(), emailPw, folderName);

  }

  @Override
  public EmailDto emailDetail(Long emailNo) {

    return emailRepository.emailDetail(emailNo);

  }

  @Override
  public void emailUpdateSeen(HttpServletRequest request, Long emailNo) {
    String folderName = request.getParameter("folderName");
    emailRepository.emailUpdateSeen(getEmployee(request).getEmployeeId(), emailPw, folderName, emailNo);
  }

  @Override
  public void moveToTrash(HttpServletRequest request, List<Long> emailNoList) {

    String folderName = request.getParameter("folderName");
    emailRepository.moveToTrash(getEmployee(request).getEmployeeId(), emailPw, folderName, emailNoList);

  }

  @Override
  public ResponseEntity<ByteArrayResource> downloadAttachment(String emailFileName) {
    return emailRepository.downloadAttachment(emailFileName);
  }

  private EmployeeDto getEmployee(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");
  }

}
