package project.soms.mypage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AttendanceDto;
import project.soms.mypage.dto.ManageListDto;
import project.soms.mypage.repository.AttendanceRepository;
import project.soms.mypage.repository.MypageRepository;
import project.soms.mypage.service.AttendanceServiceImpl;
import project.soms.mypage.service.MypageServiceImpl;

@Controller
@RequiredArgsConstructor
public class MypageController {

	private final MypageRepository mypageRepository;
	private final MypageServiceImpl mypageServiceImpl;
	
	private final AttendanceServiceImpl attendanceServiceImpl;
	private final AttendanceRepository attendanceRepository;
	
	@GetMapping("mypage")	// 마이페이지 이동
	public String mypage(Model model,HttpServletRequest req, String OffcnavasDivider, String AttendanceSelectDate, HttpServletResponse res) {
		
		// 세션에 저장된 사원의 값을 저장
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		model.addAttribute("employee", employee);
		
		// 회원가입에 사용될 직급 리스트
		List<ManageListDto> registerManages = mypageRepository.mypageRegisterManageList();
		model.addAttribute("registerManages", registerManages);
		
		// common 설정
		mypageServiceImpl.mypageCommonSetting(model, req, OffcnavasDivider);
		
		// 이번 달 부터 6개월 List 
		List<String> attendanceSixMonth = attendanceServiceImpl.getAttendanceSixMonth();
		model.addAttribute("attendanceSixMonth", attendanceSixMonth);
		
		// 셀렉트에서 선택한 값이 없을경우 이번년도 이번달을 아닐 시 선택한 달을 표시
		String attendanceSelectDate = attendanceServiceImpl.getAttendanceSelectDate(AttendanceSelectDate);
		model.addAttribute("attendanceSelectDate", attendanceSelectDate);
		
		// 출퇴근 설정
		attendanceServiceImpl.getTodayAttendanceExistence(employee.getEmployeeNo(), model, res);
		
		// 출근 관리 List
		List<AttendanceDto> attendanceList = attendanceRepository.attendanceList(employee.getEmployeeNo(), attendanceSelectDate);
		model.addAttribute("attendanceList", attendanceList);
		
		// 주간근로 계산
		String calAttendance = attendanceServiceImpl.calAttendance(employee.getEmployeeNo());
		model.addAttribute("calAttendance", Integer.parseInt(calAttendance));
		
		// 연차관련 설정
		mypageServiceImpl.mypageAnnualLeaveSetting(model, req, employee);
		
		return "mypage/mypage";
	}
	
	@PostMapping("mypage/infomation/update")
	@ResponseBody // 개인정보 업데이트
	public int infomationUpdate(EmployeeDto employee, HttpServletResponse res, HttpServletRequest req) {
		
		int count = 0;
		
		try {
			mypageRepository.mypageInfomationUpdate(employee);
			req.getSession().invalidate();
			count = 1;
		}catch(Exception ex) {
			count = 2;
		}
		
		return count;
	}
	
	@GetMapping("mypage/register")	// 계정생성 이동
	public String register(Model model, HttpServletRequest req, String OffcnavasDivider) {
		
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("LOGIN_EMPLOYEE");
		model.addAttribute("employee", employee);
		
		List<ManageListDto> registerManages = mypageRepository.mypageRegisterManageList();
		model.addAttribute("registerManages", registerManages);
		
		mypageServiceImpl.mypageCommonSetting(model, req, OffcnavasDivider);
		
		return "mypage/register";
	}
	
	@PostMapping("mypage/register/insert")	// 계정생성 인설트
	@ResponseBody
	public int registerInsert(EmployeeDto employee, HttpServletResponse res) {
		
		int count = 0;
		
		long adminNo = 0;
		
		if(employee.getManageNo() == 11 || employee.getManageNo() == 13) {
			adminNo = 13;
		}else {
			adminNo = mypageRepository.getEmployeeManageNo(employee.getEmployeeAdmin());
		}
		
		
		// 생성할 사번과 관리자 사번의 직급 검증
		if(employee.getManageNo() > adminNo) {
			count = 1;
		}else {
			if(mypageRepository.mypageRegisterUniqueCheck(employee) > 0) {
				count = 2;
			}else {
				try {
					mypageRepository.mypageRegisterInsert(employee);
					count = 3;
				}catch(Exception e) {
					count = 4;
				}
			}
		}

		return count;
	}
	
	@PostMapping("mypage/search/replace")	// 사원조회 modal
	@ResponseBody
	public List<String> mypageSearchReplace(long employeeNo){
		EmployeeDto search_employee = mypageRepository.mypageEmployeeSearch(employeeNo);
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
	
	@PostMapping("mypage/adminin/update")
	@ResponseBody
	public int mypageAdminUpdate(EmployeeDto employee, HttpServletResponse res){
	
		int count = 0;
		
		long adminNo = 0;
		
		if(employee.getManageNo() == 11 || employee.getManageNo() == 13) {
			adminNo = 13;
		}else {
			adminNo = mypageRepository.getEmployeeManageNo(employee.getEmployeeAdmin());
		}
		
		// 생성할 사번과 관리자 사번의 직급 검증   
		if(employee.getManageNo() > adminNo) {
			count = 1;
		}else {
			try {
				mypageRepository.mypageInfomationUpdate(employee); 
				count = 2;
			}catch(Exception ex) {
				count = 3;
			}
		}
		
		return count;
	}
	
	@PostMapping("mypage/delete")
	@ResponseBody
	public int mypageDelete(long employeeNo) {
		
		int count = 0;
		
		try {
			mypageRepository.mypageDelete(employeeNo);
			mypageRepository.mypageDeleteNull(employeeNo);
			count = 1;
		}catch(Exception ex) {
			count = 2;
		}
		
		return count;
	}
	
	
}
