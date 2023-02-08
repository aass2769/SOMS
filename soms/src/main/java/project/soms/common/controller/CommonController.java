package project.soms.common.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import project.soms.common.dto.CommonDto;
import project.soms.common.service.CommonService;

@Controller
@RequiredArgsConstructor
public class CommonController {
	
	private final CommonService commonService;

	@GetMapping("common")
	public String common(Model model) {
		
		String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};
		
		for (int i = 0; i < teams.length; i++) {
			List<CommonDto> teamEmployeeList = commonService.commonList(teams[i]);
			
			model.addAttribute("team" + i, teamEmployeeList);
		}
		
		List<CommonDto> executiveList = commonService.executiveList(teams);
		model.addAttribute("teamExecutive", executiveList);
		return "common/common";
	}

}
