package project.soms.email.repository;

import project.soms.email.dto.EmailDto;

import java.util.List;

public interface EmailRepository {

  /**
   * email내역 반환
   * 해당 임직원의 아이디와 비밀번호를 조건으로 이메일 서버와 연결
   * 해당 폴더 이름으로 조건 설정
   * 폴더 이름 = 'inbox', 'sent', 'junk item' 등등
   */
  List<EmailDto> emailList(String employeeId, String employeePw, String mailFolder);

  EmailDto emailDetail(Long emailNo);

  void moveToTrash(String employeeId, String employeePw, String mailFolder, Long emailNo);

}
