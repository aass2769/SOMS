package project.soms.board.repository;

import java.util.List;

import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.employee.dto.EmployeeDto;

public interface BoardRepository{

	List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput);
	
	List<BoardDto> selectNoticeBoard(String boardSection, String selectList, String searchInput);
	
	void insertBoard(BoardDto boardDto);
	
	void updateBoard(BoardDto readBoardDto);

	void updateViews(Integer boardNo);
	
	BoardDto readBoard(Integer boardNo);
	
	List<CommentDto> selectComment(Integer boardNo);
	
	void writeComment(String commentContent, EmployeeDto employeeDto, Integer boardNo, String commentDate);
}
