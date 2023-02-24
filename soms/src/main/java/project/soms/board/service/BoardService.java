package project.soms.board.service;

import java.util.List;
import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.employee.dto.EmployeeDto;

public interface BoardService {

	//공지없음 게시글 목록 SELECT
	List<BoardDto> selectBoard(String boardSection, BoardDto boardDto);
	
	//공지있음 게시글 목록 SELECT
	List<BoardDto> selectNoticeBoard(String boardSection, BoardDto boardDto);
	
	//총 게시물 수 select 메서드
	int selectBoardTotal(String boardSection);
	
	//게시글 insert 메서드
	void insertBoard(BoardDto boardDto);
	
	//게시글 update 메서드
	void updateBoard(BoardDto readBoardDto);
	
	//게시글 delete 메서드
	void deleteBoard(Integer boardNo);
	
	//게시글 상세보기 select 메서드
	BoardDto readBoard(Integer boardNo);
	
	//조회 수 update 메서드
	void updateViews(Integer boardNo);
	
	//댓글 select 메서드
	List<CommentDto> selectComment(Integer boardNo);
	
	//댓글 insert 메서드
	void writeComment(String commentContent, EmployeeDto employeeDtom, Integer boardNo);
	
	//댓글 delete 메서드
	void deleteComment(CommentDto commentDto);
	
	//게시글 다음글 이전글 메서드
	BoardDto readBoardMove(String boardSection, Integer boardNo);
}
