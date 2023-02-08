package project.soms.common.service;

import java.util.List;

import project.soms.common.dto.CommonDto;

public interface CommonService {

	List<CommonDto> commonList(String team);
	
	List<CommonDto> executiveList(String[] team);
}
