package project.soms.mypage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import project.soms.common.dto.CommonDto;
import project.soms.common.service.CommonService;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.repository.MypageRepository;
import project.soms.mypage.dto.AnnualLeaveDto;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

	private final CommonService commonService;
	private final MypageRepository mypageRepository;
	
	@Override
	public void mypageCommonSetting(Model model, HttpServletRequest req,String OffcnavasDivider) {
	
		String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};
		
		if(OffcnavasDivider != null) {
			String employeeTeam = req.getParameter("employeeTeam");
			String manage = req.getParameter("manage");
			String employeeName = req.getParameter("employeeName");
			
			// 검색 조건 확인 및 검색 리스트 생성
			if(employeeTeam != null || manage != null || (employeeName != null && employeeName != "")) {
				List<CommonDto> selectList = commonService.selectedCommon(teams, employeeTeam, manage, employeeName);
				model.addAttribute(OffcnavasDivider, selectList);
				model.addAttribute("selectList", "ok");
			}
		}

		//각 팀과 팀에 속한 인원의 직급, 이름, 사번을 가지고옴.
		for (int i = 1; i <= teams.length; i++) {
			List<CommonDto> teamEmployeeList = commonService.commonList(teams[i-1]);
			
			model.addAttribute("team" + i, teamEmployeeList);
		}
		
		//임원들의 리스트를 가지고옴. 공통, 경영 지원, 개발 연구, 영업 임원들이 있음.
		List<CommonDto> executiveList = commonService.executiveList(teams);
		
		model.addAttribute("team0", executiveList);
	}

	@Override
	public void mypageAnnualLeaveSetting(Model model, HttpServletRequest req, EmployeeDto employee) {
		// 세션에 담긴 사원 객체 및 사번 모델에 추가
		long employeeNo = employee.getEmployeeNo();
		
		// 현재 날과 년도를 구분 후 출퇴근 버튼에 사용
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String now = sdf.format(date);
		Integer nowYear = Integer.parseInt(now.substring(0, 4));
		
		// 연차 사용 리스트
		List<AnnualLeaveDto> annualListConvert = mypageRepository.getEmployeeAnnualLeave(employeeNo, nowYear);
		List<AnnualLeaveDto> annualList = new ArrayList<AnnualLeaveDto>();
		
		// 연차 연산
		Integer annualHaveDate = 0;
		Integer convertTime = 0;
		
		if(annualListConvert != null) {				
			for(AnnualLeaveDto annualLeaveDto :annualListConvert) {
				if(annualLeaveDto != null && annualLeaveDto.getProposerEmployeeNo() != null
				&& annualLeaveDto.getSubmissionStatus() == 2 && annualLeaveDto.getApproverEmployeeNo() == null) {
					annualHaveDate += annualLeaveDto.getDateDiff();
					annualList.add(annualLeaveDto);
					convertTime+= annualLeaveDto.getAnnualLeaveTime();
				}
			}
		}
				
		// 연차 계산
		if(convertTime >= 8) {
			annualHaveDate = annualHaveDate + (convertTime / 8);
		}
				
		Collections.reverse(annualList);
		model.addAttribute("annualList", annualList);
		model.addAttribute("annualHaveDate", annualHaveDate);
		
	}

}
