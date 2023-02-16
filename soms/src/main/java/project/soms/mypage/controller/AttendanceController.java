package project.soms.mypage.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import project.soms.employee.dto.EmployeeDto;
import project.soms.mypage.dto.ManageDto;
import project.soms.mypage.repository.AttendanceRepository;
import project.soms.mypage.repository.mapper.MypageMapper;
import project.soms.mypage.service.AttendanceServiceImpl;


@Controller
@RequiredArgsConstructor
public class AttendanceController {
	
	private final AttendanceServiceImpl attendanceServiceImpl;
	
	private final AttendanceRepository attendanceRepository;
	
	private final MypageMapper mypageMapper;
	
	@GetMapping("attendance")
	public String attendance(Model model, HttpServletRequest req, String attendanceSelectDate) {
		
		EmployeeDto employee = (EmployeeDto) req.getSession().getAttribute("employee");
		long employeeNo = employee.getEmployeeNo();
		
		// 회원가입에 사용될 매니지 list
		List<ManageDto> manages = mypageMapper.getManages();
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
		List<String> attendances = attendanceRepository.attendanceCheck(employeeNo, attendanceDate);
		model.addAttribute("attendanceList", attendances);
		
		return "mypage/mypage";
	}
	
	@GetMapping("attendance.gotowork")
	@ResponseBody
	public void gotowork(HttpServletRequest req) {
		attendanceServiceImpl.workcheck(req);
	}
	
	@GetMapping("attendance.dummy")
	public String attendanceDummy() {
		return "mypage/AttendanceDummy";
	}
	
}
