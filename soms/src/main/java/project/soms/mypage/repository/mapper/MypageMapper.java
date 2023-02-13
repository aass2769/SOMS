package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.ManageDto;

@Repository
@Mapper
public interface MypageMapper {
	
	// 개인정보 수정 비밀번호 변경 
	public void changePassword(@Param("pw") String pw, @Param("no") long no);
	
	// 개인정보 수정 이메일 변경
	public void changeEmail(@Param("email") String email, @Param("no") long no);
	
	// 개인정보 수정 이메일 비밀번호 변경
	public void changeEmailPw(@Param("emailpw") String emailpw, @Param("no") long no);
	
	// 직급 가져오기
	public List<ManageDto> getManages();
	
	// 계정생성
	public void register(@Param("employee") EmployeeDto employee );
	
}
