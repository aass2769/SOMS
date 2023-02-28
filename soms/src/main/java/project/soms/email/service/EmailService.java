package project.soms.email.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import project.soms.email.dto.EmailDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EmailService {

  List<EmailDto> emailList(HttpServletRequest request);

  EmailDto emailDetail(Long emailNo);

  void emailUpdateSeen(HttpServletRequest request, Long emailNo);

  void moveToTrash(HttpServletRequest request, List<Long> emailNoList);

  ResponseEntity<ByteArrayResource> downloadAttachment(String emailFileName);
}
