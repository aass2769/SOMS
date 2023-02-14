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
	
	// 검색 메서드
	@GetMapping("common")
	public String commonSelect(Model model, String employeeTeam, String manage, String employeeName) {
		String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};
		
		// 검색 조건 확인 및 검색 리스트 생성
		if(employeeTeam != null || manage != null || employeeName != "") {
			
			List<CommonDto> selectList = commonService.selectedCommon(teams, employeeTeam, manage, employeeName);
			model.addAttribute("commonSelect", selectList);
		}
		
		//기본 전체 리스트
		nomalList(model, teams);
		
		return "common/common";
		
	}

	//각 팀의 인원과 직급 이름 사번을 가지고오는 메서드
	private void nomalList(Model model, String[] teams) {
		
		//각 팀과 팀에 속한 인원의 직급, 이름, 사번을 가지고옴.
		for (int i = 1; i <= teams.length; i++) {
			List<CommonDto> teamEmployeeList = commonService.commonList(teams[i-1]);
			
			model.addAttribute("team" + i, teamEmployeeList);
		}
		
		//임원들의 리스트를 가지고옴. 공통, 경영 지원, 개발 연구, 영업 임원들이 있음.
		List<CommonDto> executiveList = commonService.executiveList(teams);
		
		model.addAttribute("team0", executiveList);
	}
	

}
