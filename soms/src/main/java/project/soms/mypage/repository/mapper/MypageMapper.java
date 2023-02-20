package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AnnualLeaveDto;
import project.soms.mypage.dto.ManageDto;

@Repository
@Mapper
public interface MypageMapper {
	
	// 직급 가져오기 
	public String getManage(long manageNo);
	
	// 직급 가져오기 List
	public List<ManageDto> getManages();
	
	public void mypageInfomationUpdate(EmployeeDto employee);

	public long getManageNo(long employeeAdmin);

	public Integer getEmployeeCheck(EmployeeDto employee);
	
	public void register(EmployeeDto employee);
	
	public EmployeeDto searchModalEmployeePrivacy(long employeeNo);
	
	public List<AnnualLeaveDto> getEmployeeAnnualLeave(@Param("employeeNo") long employeeNo, @Param("year") Integer year);
	
}
