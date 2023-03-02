package project.soms.mypage.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import project.soms.employee.dto.EmployeeDto;

public interface MypageService {

	void mypageCommonSetting(Model model, HttpServletRequest request, String OffcnavasDivider);
	
	void mypageAnnualLeaveSetting(Model model, HttpServletRequest req, EmployeeDto employee);
}
