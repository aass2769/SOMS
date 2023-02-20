package project.soms.board.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.employee.dto.EmployeeDto;

@Mapper
public interface BoardMapper {

	List<BoardDto> selectBoard(@Param("boardSection") String boardSection, @Param("boardDto") BoardDto boardDto);
	
	List<BoardDto> selectNoticeBoard(@Param("boardSection") String boardSection, @Param("boardDto") BoardDto boardDto);
	
	int selectBoardTotal(String boardSection);
	
	void insertBoard(BoardDto boardDto);
	
	void updateBoard(BoardDto readBoardDto);
	
	void deleteBoard(Integer boardNo);
	
	void updateViews(Integer boardNo);
	
	BoardDto readBoard(Integer boardNo);
	
	List<CommentDto> selectComment(Integer boardNo);
	
	void writeComment(@Param("commentContent") String commentContent, @Param("employeeDto") EmployeeDto employeeDto, @Param("boardNo") Integer boardNo, @Param("commentDate") String commentDate);
}
