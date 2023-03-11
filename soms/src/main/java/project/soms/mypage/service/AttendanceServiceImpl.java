package project.soms.mypage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.soms.mypage.dto.AttendanceDto;
import project.soms.mypage.dto.OvertimeDto;
import project.soms.mypage.repository.AttendanceRepository;
import project.soms.mypage.repository.MypageRepository;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
	
	private final AttendanceRepository attendanceRepository;
	
	private final MypageRepository mypageRepository;
	
	@Override	// 값이 있을 경우 그대로, 없을경우 이번년도 달을 반환
	public String getAttendanceSelectDate(String AttendanceSelectDate) {
		if(AttendanceSelectDate == null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(now);
		}else {
			return AttendanceSelectDate;
		}
	}
	
	@Override	// 6개월간의 List
	public List<String> getAttendanceSixMonth(){
		
		List<String> attendanceSixMonth = new ArrayList<>();
		
		for(int i = 0; i < 6; i++) {
			LocalDate localNow = LocalDate.now().minusMonths(i);
			String localNow_Text = localNow.toString().substring(0,7);
			attendanceSixMonth.add(localNow_Text);
		}
		
		Collections.reverse(attendanceSixMonth);
		
		return attendanceSixMonth;
	}

	@Override //주간근로 계산
	public String calAttendance(long employeeNo) {
		
		Integer DayWorkingHours = 0;
		
		Integer weekWorkingHours = 0;
		
		Integer GoToTime = 0;
		
		Integer LeaveToTime = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		
		for(int i = 0; i<7; i++) {;
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
			String Today = format.format(cal.getTime());
			
			AttendanceDto times = attendanceRepository.calAttendance(employeeNo, Today);
			
			if(times != null) {				
				
				if (times.getAttendanceLeavetotime() == 0) {
					GoToTime = 0;
					LeaveToTime = 0;
				}else {
					GoToTime = times.getAttendanceGototime();
					LeaveToTime = times.getAttendanceLeavetotime();
				}
				
				DayWorkingHours = LeaveToTime - GoToTime;
				weekWorkingHours += DayWorkingHours;
			}
		}
		
		return weekWorkingHours.toString();
	}

	@Override	// 출퇴근 버튼 관리
	public void getTodayAttendanceExistence(long employeeNo, Model model,HttpServletResponse res){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String today = sdf.format(now).substring(0,10);
		
		// 값이 0일 경우 오늘 퇴근 안함,0 이상일경우 오늘 출퇴근 완료
		Integer existence = attendanceRepository.getTodayAttendanceExistence(employeeNo, today);
		
		if(existence>0) {
			model.addAttribute("attendanceExistence", "true");
		}else {
			model.addAttribute("attendanceExistence", "false");
		}
		
		int gotime = 9;
		int leavetime = 18;
		
		int nowtime = Integer.parseInt(sdf.format(now).substring(11, 13));
		
		int nowminute = Integer.parseInt(sdf.format(now).substring(14, 16));

		// 당일 연장근무가 있을 경우 연장근무로 끝나는시간을 변경

		try {
			OvertimeDto overtime = mypageRepository.getEmployeeOvertime(employeeNo, today);

			if(overtime.getSubmissionStatus() == 2 && overtime.getApproverEmployeeNo() == null &&
					overtime.getProposerEmployeeNo() != null && overtime != null) {

				if(overtime.getOvertimeStartTime() < gotime) {
					gotime = overtime.getOvertimeStartTime();
				}else {
					leavetime = overtime.getOvertimeEndTime();
				}
			}
		} catch (Exception e) {
			log.info("연장근로 값 없음");
		}

		Integer attendance = 3;
		
		if((nowtime==(gotime-1))&&(nowminute>=50) || (nowtime>=gotime)) {
			
			String attendnaceCheck = attendanceRepository.attendanceCheck(employeeNo);
			
			if(attendnaceCheck == null) {
				attendance = 2;
			}else {
				attendance = 1;
			}
		}
		
		AttendanceDto attendanceRecent = attendanceRepository.getAttendanceRecent(employeeNo);
		
		if(attendanceRecent == null) {
			Integer a = 0;
			attendanceRecent = new AttendanceDto("null", a, a);
		}
		
		// 시간이지나 자동퇴근
		if(nowtime >= leavetime) {
			attendance = 3;
			if(attendanceRecent.getAttendanceLeavetotime() == 0 && attendanceRecent.getAttendanceGototime() != 0) {
				AttendanceDto attendanceDto = new AttendanceDto(leavetime, employeeNo);
				attendanceRepository.attendanceUpdate(attendanceDto);
				attendance = 4;
			}
		}
		
		// 전날에 퇴근안찍고 갔을 경우
		if(!attendanceRecent.getAttendanceGotodate().equals(today)) {
			if(attendanceRecent.getAttendanceLeavetotime() == 0 && attendanceRecent.getAttendanceGototime() != 0) {
				AttendanceDto attendanceDto = new AttendanceDto(leavetime, employeeNo);
				attendanceRepository.attendanceUpdate(attendanceDto);
				attendance = 2;
			}
		}
		
		model.addAttribute("attendance", attendance);
		
	}
		
		
}
