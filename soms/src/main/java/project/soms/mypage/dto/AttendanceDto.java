package project.soms.mypage.dto;

import lombok.Data;

@Data
public class AttendanceDto {

	private String attendanceGotodate;
	private Integer attendanceGototime;
	private Integer attendanceLeavetotime;
	private long attendanceEmployee;

	public AttendanceDto(String attendanceGotodate, Integer attendanceGototime) {
		this.attendanceGotodate = attendanceGotodate;
		this.attendanceGototime = attendanceGototime;
	}
	
	public AttendanceDto(Integer attendanceLeavetotime, long attendanceEmployee) {
		this.attendanceLeavetotime = attendanceLeavetotime;
		this.attendanceEmployee = attendanceEmployee;
	}
	
	public AttendanceDto(String attendanceGotodate, Integer attendanceGototime, Integer attendanceLeavetotime) {
		this.attendanceGotodate = attendanceGotodate;
		this.attendanceGototime = attendanceGototime;
		this.attendanceLeavetotime = attendanceLeavetotime;
	}
	
	public AttendanceDto(String attendanceGotodate, Integer attendanceGototime, long attendanceEmployee) {
		this.attendanceGotodate = attendanceGotodate;
		this.attendanceGototime = attendanceGototime;
		this.attendanceEmployee = attendanceEmployee;
	}
	

}
