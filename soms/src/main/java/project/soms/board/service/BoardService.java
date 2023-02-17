package project.soms.board.service;

import java.util.List;
import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.employee.dto.EmployeeDto;

public interface BoardService {

	//공지없음 게시글 SELECT
	List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput);
	
	//공지있음 게시글 SELECT
	List<BoardDto> selectNoticeBoard(String boardSection, String selectList, String searchInput);
	
	//게시글 insert 메서드
	void insertBoard(BoardDto boardDto);
	
	//게시글 update 메서드
	void updateBoard(BoardDto readBoardDto);
	
	BoardDto readBoard(Integer boardNo);
	
	//조회 수 업데이트 메서드
	void updateViews(Integer boardNo);
	
	List<CommentDto> selectComment(Integer boardNo);
	
	void writeComment(String commentContent, EmployeeDto employeeDtom, Integer boardNo);
}
