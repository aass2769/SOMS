package project.soms.login.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;

@Mapper
@Repository
public interface LoginMapper {
	EmployeeDto LoginCheck(@Param("id") String id, @Param("pw") String pw);
}
