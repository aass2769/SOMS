package project.soms.mypage.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.ManageDto;

@Repository
@Mapper
public interface MypageMapper {
	
	// 직급 가져오기 
	public String getManage(long manageNo);
	
	// 직급 가져오기 List
	public List<ManageDto> getManages();
	
	public void mypageInfomationUpdate(EmployeeDto employee);

	
}
