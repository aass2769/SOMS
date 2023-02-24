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
	private Integer beforeBoardNo;
	private Integer afterBoardNo;
	private Integer boardNumber;
	// 검색 조건 (제목 or 작성자)
	private String selectList = "";
	// 검색한 내용
	private String searchInput = "";
	private Long employeeNo;
	//게시물 리스트에서 각 페이지. 페이지네이션에서 1 2 3 4 페이지가 있다면 1페이지 같이 각 페이지의 변수를 viewPage로 선언. 첫 게시글페이지는 무조건 1번이기 떄문에 1로 초기화.
	private int viewPage = 1;
	//게시글 리스트에서 페이지당 목록. 기본값 10으로 초기화
	private int pageLimit = 10;
	//게시글 목록에따라 sql쿼리에서 제외해야할 개수
	private int pageOffset = 0;
	//게시물의 시작번호.
	private int startRowNo;

	
}
