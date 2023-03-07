package project.soms.login.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import project.soms.employee.dto.EmployeeDto;

@Mapper
@Repository
public interface LoginMapper {
	// 로그인 체크 후 사원의 정보를 가져온다.
	EmployeeDto login(@Param("id") String id, @Param("pw") String pw);
}
