package project.soms.board.dto;

import lombok.Data;

@Data
public class CommentDto {

	private String commentContent;
	private String commentDate;
	private String employeeName;
	private String employeeTeam;
	private String manage;
	private Integer boardNo;
}
