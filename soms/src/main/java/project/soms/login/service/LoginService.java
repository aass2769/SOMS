package project.soms.login.service;



import javax.servlet.http.HttpServletRequest;

import project.soms.employee.dto.EmployeeDto;

public interface LoginService {
	String LoginGo(EmployeeDto employee, HttpServletRequest req);
}
