package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AnnualLeaveDto;
import project.soms.mypage.dto.ManageListDto;
import project.soms.mypage.dto.OvertimeDto;

@Mapper
public interface MypageMapper {
	// 개인정보 수정
	public void mypageInfomationUpdate(EmployeeDto employee);
	
	// 회원가입 및 개인정보용 직급 리스트
	public List<ManageListDto> mypageRegisterManageList();
	
	// 매니지 넘버 가져오기
	public Integer getEmployeeManageNo(long employeeNo);
	
	// 회원가입
	public void mypageRegisterInsert(EmployeeDto employee);
	
	// 회원가입 중복 체크
	public Integer mypageRegisterUniqueCheck(EmployeeDto employee);
	
	// Modal용 사번으로 사원정보 가져오기 
	public EmployeeDto mypageEmployeeSearch(long employeeNo);
	
	// 연차 List
	public List<AnnualLeaveDto> getEmployeeAnnualLeave(@Param("employeeNo") long employeeNo, @Param("year") Integer year);
	
	// 연장근무
	public OvertimeDto getEmployeeOvertime(@Param("employeeNo")long employeeNo, @Param("NowDate") String NowDate);
	
	// 회원 삭제
	public void mypageDelete(long employeeNo);
}
