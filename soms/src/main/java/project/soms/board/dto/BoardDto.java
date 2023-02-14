package project.soms.board.dto;

import lombok.Data;

@Data
public class BoardDto {

	private Integer boardNo;
	private String boardAnnouncement;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private Integer boardViews;
	private String employeeName;
	private String employeeTeam;
	private String manage;
	private String boardSection;
	private Integer boardListSum;
	private Integer boardNumber;
	private String selectList;
	private String searchInput;
	
}
