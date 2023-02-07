package project.soms.login.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import project.soms.employee.dto.EmployeeDto;

@Service
public class LoginServiceImpl implements LoginService{

	@Override
	public String LoginGo(EmployeeDto employee, HttpServletRequest req) {
		if(employee == null) {
			return "login/error";
		}else {
			req.getSession().setAttribute("employee", employee);
			return "login/success";
		}
	}



}
