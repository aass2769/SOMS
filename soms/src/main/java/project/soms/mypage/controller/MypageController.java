package project.soms.mypage.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.AttendanceCheckDto;
import project.soms.mypage.dto.ManageDto;
import project.soms.mypage.repository.AttendanceRepository;
import project.soms.mypage.repository.MypageRepository;
import project.soms.mypage.repository.mapper.MypageMapper;
import project.soms.mypage.service.AttendanceServiceImpl;
import project.soms.mypage.service.MypageServiceImpl;

@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageMapper mypageMapper;
	
	private final MypageServiceImpl mypageServiceImpl;
	private final MypageRepository mypageRepository;
	
	private final AttendanceRepository attendanceRepository;
	private final AttendanceServiceImpl attendanceServiceImpl;
	
	

	
	@GetMapping("mypage")
	public String mypage(Model model, HttpServletRequest req, String attendanceSelectDate) {
		
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("employee");
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
		
		System.out.println(model.getAttribute("attendance"));

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
		
		
		return "mypage/mypage";
	}	
	
	@PostMapping("mypage.infomation.update")
	public String mypageInfomationUpdate(EmployeeDto employee) {
		mypageRepository.mypageInfomationUpdate(employee);
		return "mypage/AttendanceDummy";
	}

}
