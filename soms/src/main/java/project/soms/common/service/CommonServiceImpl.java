package project.soms.common.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.soms.common.dto.CommonDto;
import project.soms.common.repository.CommonRepository;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService{
	
	private final CommonRepository commonRepository;
	
	@Override
	public List<CommonDto> commonList(String team) {
		
		List<CommonDto> commonList = commonRepository.commonList();
		List<CommonDto> teamEmployeeList = new ArrayList<>();
		for (int i = 0; i < commonList.size(); i++) {
			if (commonList.get(i).getEmployeeTeam().equals(team)) {
				teamEmployeeList.add(commonList.get(i));
			}
		}
		return teamEmployeeList;
	}

	@Override
	public List<CommonDto> executiveList(String[] team) {
		
		
		List<CommonDto> commonList = commonRepository.commonList();
		List<CommonDto> teamEmployeeList = new ArrayList<>();
		for (int i = 0; i < commonList.size(); i++) {
			if (Arrays.asList(team).indexOf(commonList.get(i).getEmployeeTeam()) < 0) {
				teamEmployeeList.add(commonList.get(i));
			}
		}
		return teamEmployeeList;
	}

	@Override
	public List<CommonDto> commonSelect(String employeeTeam, String manage, String employeeName) {
		
		List<CommonDto> commonSelect = commonRepository.commonSelect(employeeTeam, manage, employeeName);
		return commonSelect;
	}

	@Override
	public List<CommonDto> selectedCommon(String[] teams, String employeeTeam, String manage, String employeeName) {
		
		String employeeTeamCheck = employeeTeam;
		
		if(employeeTeamCheck == null || Arrays.asList(teams).indexOf(employeeTeamCheck) < 0) {
			employeeTeamCheck = "";
		} 
		if(manage == null) {
			manage = "";
		}
		
		//부서, 직급, 이름을 db에서 가지고옴
		List<CommonDto> selectList = commonSelect(employeeTeamCheck, manage, employeeName);
		
		//임원인 경우를 판별하는 부분
		List<CommonDto> list = new ArrayList<>();
	    if(Arrays.asList(teams).indexOf(employeeTeam) < 0 && employeeTeam != null) {
	      for (CommonDto select : selectList) {
	        if(Arrays.asList(teams).indexOf(select.getEmployeeTeam()) < 0) {
	          list.add(select);
	        }
	      }
	      selectList = list;
	    }
	    return selectList;
	}


}
