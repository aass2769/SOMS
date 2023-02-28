package project.soms.email.service;

import project.soms.email.dto.EmailDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EmailService {

  List<EmailDto> emailList(HttpServletRequest request);

  EmailDto emailDetail(Long emailNo);

  void moveToTrash(HttpServletRequest request);
}
