package project.soms.login.service;

import org.springframework.stereotype.Service;
import project.soms.employee.dto.EmployeeDto;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Override
	public String LoginGo(EmployeeDto employee, HttpServletRequest req) {
		if(employee == null) {
			return "login/error";
		}else {
			req.getSession().setAttribute("LOGIN_EMPLOYEE", employee);
			return "login/success";
		}
	}

}
