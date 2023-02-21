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
import project.soms.mypage.dto.OvertimeDto;
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
		// 세션에 담긴 사원 객체
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		// 세션에 담긴 사원 객체의 사번
		long employeeNo = employee.getEmployeeNo();
		// 모델에 사원 객체 추가
		model.addAttribute("employee", employee);
		
		// 개인정보란에 사용될 직급의 이름 및 모델에 추가
		String manageName = mypageMapper.getManage(employee.getManageNo());
		model.addAttribute("manageName", manageName);
		
		// 회원가입에 사용될 매니지 list
		List<ManageDto> manages = mypageMapper.getManages();
		Collections.reverse(manages);
		model.addAttribute("manages", manages);

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
		
		// common offcanvas 사용
		nomalList(model,req);
		
		// 현재 날과 년도를 구분 후 출퇴근 버튼에 사용
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Integer nowYear = Integer.parseInt(sdf.format(now).substring(0, 4));
		String nowDate = sdf.format(now);
		
		// 출퇴근 버튼에 관련된 메서드
		myAttendance(model,nowDate, employeeNo, req);
		
		// 연차 사용 리스트
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
		
		// 연차 계산
		if(convertTime >= 8) {
			annualHaveDate = annualHaveDate + (convertTime / 8);
		}
		
		model.addAttribute("annualList", annualList);
		model.addAttribute("annualHaveDate", annualHaveDate);
		
		if(searchemployeeno == null) {
			return "mypage/mypage";
		}else {
			return "redirect:" + req.getHeader("Referer");
		}

		
	}
	
	//OvertimeDto(proposerEmployeeNo=20230201006, submissionStatus=2, approverEmployeeNo=null, overtimeStartDate=2023-02-20, overtimeEndDate=2023-02-22, overtimeStartTime=18, overtimeEndTime=20)

	
	public void myAttendance(Model model, String now, long employeeNo, HttpServletRequest req) {
		// 출근 했는지 안했는지에 필요한 값
		Optional<String> bool = Optional.ofNullable(attendanceRepository.goToWorkCheck(employeeNo));
		
		if(bool.isPresent()) {
			model.addAttribute("attendance", 1); // 출근됨
		}else {
			model.addAttribute("attendance", 2); // 퇴근됨
		}
		
		// 현재 날짜
		String NowDate = now.substring(0, 10);
		// 현재 시간
		Integer NowHour = Integer.parseInt(now.substring(11, 13));
		// 현재 분
		Integer NowMin = Integer.parseInt(now.substring(14, 16));
		// 퇴근 시간
		Integer standardLeaveHour = 20;
		// 출근 시간
		Integer standardGoHour = 8;
		Integer standardGoMin = 50;
		
		
		// 예 21일 23시 -> 22일 3시까지
		
		// 연장근무 받아오기 null일 경우 연장근무가 없음
		OvertimeDto overtime = mypageMapper.getEmployeeOvertime(employeeNo, NowDate);
		
		if (overtime != null) {
			// 연장근로 시작 시간이 기본 출근시간 이전일 경우( 오전 연장근로 )
			if (overtime.getOvertimeStartTime() <= standardGoHour) {
				// 출근버튼 활성화 시간
				standardGoHour = overtime.getOvertimeStartTime() - 1;
			} else {
				// 현재날짜와 연장근로 끝나는시간이 다를경우
				if (!overtime.getOvertimeEndDate().equals(NowDate)) {
					standardLeaveHour = overtime.getOvertimeEndTime() + 24;
				}
				standardLeaveHour = overtime.getOvertimeEndTime();
			}
			
		}
				
		// (현재시간과 출근시간이 같으며 현재분이 출근분 보다 크고) or 현재 시간이 출근시간보다 클 경우
		if(((NowHour == standardGoHour)&&(NowMin >= standardGoMin)) || NowHour > standardGoHour ) {
			model.addAttribute("attendance", 2); // 출근 버튼 생성
		}
		
		// 현재시간이 퇴근시간보다 높을 경우
		if(NowHour >= standardLeaveHour) {
			model.addAttribute("attendance", 0); // 버튼 삭제
			// 퇴근이 안되어있을 경우
			if(bool.isPresent()) {
				attendanceServiceImpl.workcheck(req, 0); // 퇴근
				System.out.println("퇴근");
			}
		}

		
	}
	
	// 내 정보 업데이트 
	@PostMapping("mypage.infomation.update")
	public String mypageInfomationUpdate(EmployeeDto employee) {
		mypageRepository.mypageInfomationUpdate(employee);
		return "mypage/AttendanceDummy";
	}
	
	// 계정 생성 페이지
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
	
	// 계정생성 전송
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
	
	// 관리자가 사원조회로 사원정보를 업데이트
	@PostMapping("mypage.admin.infomation.update")
	public String mypageAdminInfomationUpdate(EmployeeDto employee) {
		mypageRepository.mypageInfomationUpdate(employee);
		return "mypage/AttendanceDummy";
	}
	
	// ajax 사원조회로 사원의 정보를 확인 후 모달에 담을 때 쓰
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
