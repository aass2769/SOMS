package project.soms.common.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.common.dto.CommonDto;


@Mapper
public interface CommonMapper {
	
	List<CommonDto> commonList();

	List<CommonDto> commonSelect(@Param("employeeTeam") String employeeTeam, @Param("manage") String manage, @Param("employeeName") String employeeName);
	
}
