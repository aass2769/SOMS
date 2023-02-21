package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AnnualLeaveDto;
import project.soms.mypage.dto.ManageDto;
import project.soms.mypage.dto.OvertimeDto;

@Repository
@Mapper
public interface MypageMapper {
	
	// 직급 가져오기 
	public String getManage(long manageNo);
	
	// 직급 가져오기 List
	public List<ManageDto> getManages();
	
	// 내정보 업데이트
	public void mypageInfomationUpdate(EmployeeDto employee);

	// 어드민의 정보를 가져온다
	public long getManageNo(long employeeAdmin);

	// 사번과 아이디를 가진 사원이 있는지 찾는다
	public Integer getEmployeeCheck(EmployeeDto employee);
	
	// 회원가입 인설트
	public void register(EmployeeDto employee);
	
	// ajax로 key값을받아 사원을 조회히 DTO로 받아온다
	public EmployeeDto searchModalEmployeePrivacy(long employeeNo);
	
	// 연차조회
	public List<AnnualLeaveDto> getEmployeeAnnualLeave(@Param("employeeNo") long employeeNo, @Param("year") Integer year);
	
	// 연장근로 조회
	public OvertimeDto getEmployeeOvertime(@Param("employeeNo") long employeeNo, @Param("day") String now);
	
}
