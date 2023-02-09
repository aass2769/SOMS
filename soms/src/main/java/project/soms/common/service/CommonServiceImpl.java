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


}
