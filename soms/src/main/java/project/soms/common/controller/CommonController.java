package project.soms.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.soms.common.dto.CommonDto;
import project.soms.common.service.CommonService;

@Controller
@RequiredArgsConstructor
public class CommonController {
	
	private final CommonService commonService;

	@GetMapping("/common")
	public String common(Model model, HttpServletRequest request) {
		
		nomalList(model);
		return "common/common";
	}
	
	@GetMapping("/common/submit")
	public String commonSelect(Model model, String employeeTeam, String manage, String employeeName) {
		
		System.out.println(employeeTeam);
		System.out.println(manage);
		System.out.println(employeeName);
		
		if(employeeTeam == null) {
			employeeTeam = "";
		}
		if(manage == null) {
			manage = "";
		}
		if(employeeName == null) {
			employeeName = "";
		}
		
		nomalList(model);
		
		
		List<CommonDto> selectList = commonService.commonSelect(employeeTeam, manage, employeeName);
		model.addAttribute("commonSelect", selectList);
		
		if(selectList.isEmpty()) {
			System.out.println("문자열의 길이가 0입니다");
		} else {
			System.out.println("문자열의 길이가 있습니다.");
		}
		
		return "common/common";
	}

	private void nomalList(Model model) {
		String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};
		
		for (int i = 0; i < teams.length; i++) {
			List<CommonDto> teamEmployeeList = commonService.commonList(teams[i]);
			
			model.addAttribute("team" + i, teamEmployeeList);
		}
		
		List<CommonDto> executiveList = commonService.executiveList(teams);
		model.addAttribute("teamExecutive", executiveList);
	}
	

}
