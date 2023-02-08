package project.soms.common.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.common.dto.CommonDto;
import project.soms.common.repository.mapper.CommonMapper;

@Repository
@RequiredArgsConstructor
public class CommonRepositoryImpl implements CommonRepository{
	
	private final CommonMapper commonMapper;

	@Override
	public List<CommonDto> commonList() {
		List<CommonDto> commonList = commonMapper.commonList();
		return commonList;
	}
	
	

}
