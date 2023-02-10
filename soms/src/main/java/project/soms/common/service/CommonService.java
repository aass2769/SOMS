package project.soms.common.service;

import java.util.List;

import project.soms.common.dto.CommonDto;

public interface CommonService {

	//각 팀, 이름, 직급, 사번을 가져오는 메서드
	List<CommonDto> commonList(String team);
	
	//임원들의 리스트를 가져오는 메서드
	List<CommonDto> executiveList(String[] team);
	
	//검색 메서드
	List<CommonDto> commonSelect(String employeeTeam, String manage, String employeeName);
	
	List<CommonDto> selectedCommon(String[] teams, String employeeTeam, String manage, String employeeName);
	
}
