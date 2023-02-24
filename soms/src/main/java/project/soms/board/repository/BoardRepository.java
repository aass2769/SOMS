package project.soms.board.repository;

import java.util.List;

import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.employee.dto.EmployeeDto;

public interface BoardRepository{

	List<BoardDto> selectBoard(String boardSection, BoardDto boardDto);
	
	List<BoardDto> selectNoticeBoard(String boardSection, BoardDto boardDto);
	
	int selectBoardTotal(String boardSection);
	
	void insertBoard(BoardDto boardDto);
	
	void updateBoard(BoardDto readBoardDto);
	
	void deleteBoard(Integer boardNo);

	void updateViews(Integer boardNo);
	
	BoardDto readBoard(Integer boardNo);
	
	List<CommentDto> selectComment(Integer boardNo);
	
	void writeComment(String commentContent, EmployeeDto employeeDto, Integer boardNo, String commentDate);
	
	void deleteComment(CommentDto commentDto);
	
	BoardDto readBoardMove(String boardSection, Integer boardNo);
}
