package project.soms.employee.dto;

import lombok.Data;

@Data
public class EmployeeDto {
	private long employeeNo;
	private String employeeId;
	private String employeePw;
	private String employeeName;
	private String employeePhone;
	private String employeeEmail;
	private String employeeAddr;
	private String employeeTeam;
	private long employeeAdmin;
	private long manageNo;
}
