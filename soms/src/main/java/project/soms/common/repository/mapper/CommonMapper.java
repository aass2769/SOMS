package project.soms.common.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.soms.common.dto.CommonDto;


@Mapper
public interface CommonMapper {
	
	List<CommonDto> commonList();

}
