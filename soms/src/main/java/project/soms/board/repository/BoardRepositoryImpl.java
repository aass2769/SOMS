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
	public List<BoardDto> selectBoard(String boardSection, BoardDto boardDto) {
		
		List<BoardDto> boardList = boardMapper.selectBoard(boardSection, boardDto);
		return boardList;
	}

	@Override
	public List<BoardDto> selectNoticeBoard(String boardSection, BoardDto boardDto) {
		
		List<BoardDto> noticeBoardList = boardMapper.selectNoticeBoard(boardSection, boardDto);
		return noticeBoardList;
	}
	
	@Override
	public int selectBoardTotal(String boardSection) {
		
		int total = boardMapper.selectBoardTotal(boardSection);
		return total;
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
	public void deleteBoard(Integer boardNo) {

		boardMapper.deleteBoard(boardNo);
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

	@Override
	public void deleteComment(CommentDto commentDto) {

		boardMapper.deleteComment(commentDto);
	}

	@Override
	public BoardDto readBoardMove(String boardSection, Integer boardNo) {
		
		BoardDto boardPage = boardMapper.readBoardMove(boardSection, boardNo);
		return boardPage;
	}

	

	

	

	

}
