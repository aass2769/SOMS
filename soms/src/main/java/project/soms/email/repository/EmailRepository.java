package project.soms.email.repository;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import project.soms.email.dto.EmailDto;
import project.soms.employee.dto.EmployeeDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface EmailRepository {

  /**
   * email내역 반환
   * 해당 임직원의 아이디와 비밀번호를 조건으로 이메일 서버와 연결
   * 해당 폴더 이름으로 조건 설정
   * 폴더 이름 = 'inbox', 'sent', 'junk item' 등등
   */
  List<EmailDto> emailList(String employeeId, String employeePw, String folderName);

  EmailDto emailDetail(Long emailNo);

  void emailUpdateSeen(String employeeId, String employeePw, String folderName, Long emailNo);

  void moveToTrashOrJunk(String employeeId, String employeePw, String folderName, String moveFolder, List<Long> emailNoList);

  void emailSend(EmailDto emailDto, EmployeeDto employee, String employeePw) throws FileNotFoundException;

  void deleteMessage(String employeeId, String employeePw, String moveFolder, List<Long> emailNoList);

  ResponseEntity<ByteArrayResource> downloadAttachment(String emailFileName);

}
