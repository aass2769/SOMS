package project.soms.common.repository;

import java.util.List;

import project.soms.common.dto.CommonDto;

public interface CommonRepository {
	
	List<CommonDto> commonList();
	
	List<CommonDto> commonSelect(String employeeTeam, String manage, String employeeName);

}
