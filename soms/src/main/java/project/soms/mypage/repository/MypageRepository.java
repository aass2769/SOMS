package project.soms.mypage.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.repository.mapper.MypageMapper;

@Repository
@RequiredArgsConstructor
public class MypageRepository {
	
	private final MypageMapper mypageMapper;
	
	public void mypageInfomationUpdate(EmployeeDto employee){
		mypageMapper.mypageInfomationUpdate(employee);
	}

}
