package project.soms.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.board.repository.mapper.BoardMapper;
import project.soms.employee.dto.EmployeeDto;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

	private final BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput) {
		
		List<BoardDto> boardList = boardMapper.selectBoard(boardSection, selectList, searchInput);
		return boardList;
	}

	@Override
	public List<BoardDto> selectNoticeBoard(String boardSection, String selectList, String searchInput) {
		
		List<BoardDto> noticeBoardList = boardMapper.selectNoticeBoard(boardSection, selectList, searchInput);
		return noticeBoardList;
	}
	
	@Override
	public void insertBoard(BoardDto boardDto) {

		boardMapper.insertBoard(boardDto);
		
	}
	
	@Override
	public void updateBoard(BoardDto readBoardDto) {
		
		boardMapper.updateBoard(readBoardDto);
	}

	@Override
	public void updateViews(Integer boardNo) {
		
		boardMapper.updateViews(boardNo);
	}
	
	@Override
	public BoardDto readBoard(Integer boardNo) {
		
		BoardDto readBoardDto = boardMapper.readBoard(boardNo);
		return readBoardDto;
	}

	@Override
	public List<CommentDto> selectComment(Integer boardNo) {
		List<CommentDto> commentList = boardMapper.selectComment(boardNo);
		return commentList;
	}

	@Override
	public void writeComment(String commentContent, EmployeeDto employeeDto, Integer boardNo, String commentDate) {
		
		boardMapper.writeComment(commentContent, employeeDto, boardNo, commentDate);
		
	}

	

	

	

	

}
