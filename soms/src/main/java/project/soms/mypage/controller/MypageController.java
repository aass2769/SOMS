package project.soms.mypage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.common.dto.CommonDto;
import project.soms.common.service.CommonService;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AnnualLeaveDto;
import project.soms.mypage.dto.AttendanceCheckDto;
import project.soms.mypage.dto.ManageDto;
import project.soms.mypage.repository.AttendanceRepository;
import project.soms.mypage.repository.MypageRepository;
import project.soms.mypage.repository.mapper.MypageMapper;
import project.soms.mypage.service.AttendanceServiceImpl;

@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageMapper mypageMapper;
	
	private final MypageRepository mypageRepository;
	
	private final AttendanceRepository attendanceRepository;
	private final AttendanceServiceImpl attendanceServiceImpl;
	
	private final CommonService commonService;
	
	@GetMapping("mypage")
	public String mypage(Model model, HttpServletRequest req, String attendanceSelectDate, String searchemployeeno) {
		
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		long employeeNo = employee.getEmployeeNo();
				
		model.addAttribute("employee", employee);
		
		String manageName = mypageMapper.getManage(employee.getManageNo());
		model.addAttribute("manageName", manageName);
		
		// 회원가입에 사용될 매니지 list
		List<ManageDto> manages = mypageMapper.getManages();
		Collections.reverse(manages);
		model.addAttribute("manages", manages);
		
		// 출근 했는지 안했는지에 필요한 값
		Optional<String> bool = Optional.ofNullable(attendanceRepository.goToWorkCheck(employeeNo));
		
		if(bool.isPresent()) {
			model.addAttribute("attendance", 1);
		}else {
			model.addAttribute("attendance", 2);
		}

		// Select에 보여줄 6개월간의 달
		List<String> attendanceAtSixMonths = attendanceServiceImpl.getAttendanceAtSixMonths();
		model.addAttribute("attendanceAtSixMonths",attendanceAtSixMonths);
		
		// 출근관리 선택한 달 or 이번 달
		String attendanceDate = attendanceServiceImpl.getAttendanceDate(attendanceSelectDate);
		model.addAttribute("attendanceDate", attendanceDate);
		
		// 출근 관리 List
		List<AttendanceCheckDto> attendances = attendanceRepository.attendanceCheck(employeeNo, attendanceDate);
		model.addAttribute("attendanceList", attendances);
		
		// 주간근로 계산
		String weekWorkingHours = attendanceServiceImpl.getWeekWorkTime(employeeNo);		
		model.addAttribute("weekWorkingHours", weekWorkingHours);
		
		nomalList(model,req);
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer nowYear = Integer.parseInt(sdf.format(now));
		
		
		List<AnnualLeaveDto> annualListConvert = mypageMapper.getEmployeeAnnualLeave(employeeNo, nowYear);
		List<AnnualLeaveDto> annualList = new ArrayList<AnnualLeaveDto>();
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
		
		if(convertTime >= 8) {
			annualHaveDate = convertTime / 8;
		}
		
		
		model.addAttribute("annualList", annualList);
		model.addAttribute("annualHaveDate", annualHaveDate);
		
		
		if(searchemployeeno == null) {
			return "mypage/mypage";
		}else {
			return "redirect:" + req.getHeader("Referer");
		}

		
	}
	
	@PostMapping("mypage.infomation.update")
	public String mypageInfomationUpdate(EmployeeDto employee) {
		mypageRepository.mypageInfomationUpdate(employee);
		return "mypage/AttendanceDummy";
	}
	
	@GetMapping("mypage.register")
	public String mypageRegister(Model model, HttpServletRequest req) {
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("employee");
		model.addAttribute("employee", employee);
		
		// 회원가입에 사용될 매니지 list
		List<ManageDto> manages = mypageMapper.getManages();
		Collections.reverse(manages);
		model.addAttribute("manages", manages);
		
		// 출근 했는지 안했는지에 필요한 값
		Optional<String> bool = Optional.ofNullable(attendanceRepository.goToWorkCheck(employee.getEmployeeNo()));
		
		if(bool.isPresent()) {
			model.addAttribute("attendance", 1);
		}else {
			model.addAttribute("attendance", 2);
		}

		nomalList(model,req);		
		
		return "mypage/register";
	}
	
	@PostMapping("mypage.register.submit")
	public String mypageRegisterSubmit(EmployeeDto employee) {
		
		long employeeManage = employee.getManageNo();
		long adminManage = mypageMapper.getManageNo(employee.getEmployeeAdmin());
	
		Integer employeeCheck = mypageMapper.getEmployeeCheck(employee);
		
		if(adminManage>employeeManage) {
			
			if(employeeCheck > 0) {
				return "mypage/RegisterNoUnique";
			}else {
				mypageMapper.register(employee);
				return "mypage/AttendanceDummy";
			}
			
			
		}else {
			return "mypage/RegisterError";
		}
		
	}
	
	@PostMapping("mypage.admin.infomation.update")
	public String mypageAdminInfomationUpdate(EmployeeDto employee) {
		mypageRepository.mypageInfomationUpdate(employee);
		return "mypage/AttendanceDummy";
	}
	
	@PostMapping("mypageModalsReplace")
	@ResponseBody
	public List<String> mypageModalsReplace(long employeeNo){
		EmployeeDto search_employee = mypageMapper.searchModalEmployeePrivacy(employeeNo);
		ArrayList<String> employeePrivacy = new ArrayList<String>();
		employeePrivacy.add(String.valueOf(search_employee.getEmployeeNo()));
		employeePrivacy.add(search_employee.getEmployeeName());
		employeePrivacy.add(search_employee.getEmployeeId());
		employeePrivacy.add(search_employee.getEmployeePw());
		employeePrivacy.add(search_employee.getEmployeeEmail());
		employeePrivacy.add(search_employee.getEmployeePhone());
		employeePrivacy.add(search_employee.getEmployeeAddr());
		employeePrivacy.add(String.valueOf(search_employee.getEmployeeAdmin()));
		employeePrivacy.add(search_employee.getEmployeeTeam());
		employeePrivacy.add(String.valueOf(search_employee.getManageNo()));
		return employeePrivacy;
	}
	
	
	//각 팀의 인원과 직급 이름 사번을 가지고오는 메서드
	private void nomalList(Model model, HttpServletRequest request) {
		String[] teams = {"경영 지원-경영 관리", "경영 지원-재무 회계", "경영 지원-정보 보안" , "경영 지원-구매팀", "개발 연구-개발 1팀", "개발 연구-개발 2팀", "개발 연구-연구팀", "영업-홍보 마케팅", "영업-해외 사업"};

		String employeeTeam = request.getParameter("employeeTeam");
		String manage = request.getParameter("manage");
		String employeeName = request.getParameter("employeeName");

		// 검색 조건 확인 및 검색 리스트 생성
		if(employeeTeam != null || manage != null || (employeeName != null && employeeName != "")) {
			List<CommonDto> selectList = commonService.selectedCommon(teams, employeeTeam, manage, employeeName);
			model.addAttribute("commonSelect", selectList);
			model.addAttribute("selectList", "ok");
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

	

}
