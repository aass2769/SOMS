package project.soms.board.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.board.repository.BoardRepository;
import project.soms.employee.dto.EmployeeDto;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardRepository boardRepository;
	
	@Override
	public List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput) {
		
		List<BoardDto> boardList = boardRepository.selectBoard(boardSection, selectList, searchInput);
		
//		LocalDateTime now = LocalDateTime.now();
//		LocalDate today = now.toLocalDate();
//		
//		for(int i = 0; i < boardList.size(); i++) {
//			LocalDateTime boardDate = LocalDateTime.parse(boardList.get(i).getBoardDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//			LocalDate boardDateOnly = boardDate.toLocalDate();
//			
//			if(boardDateOnly.isBefore(today)) {
//				boardDate = boardDateOnly.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//				boardList.get(i).setBoardDate(boardDate);
//			}
//		}
		return boardList;
	}
	
	@Override
	public List<BoardDto> selectNoticeBoard(String boardSection, String selectList, String searchInput) {

		List<BoardDto> noticeBoardList = boardRepository.selectNoticeBoard(boardSection, selectList, searchInput);
		return noticeBoardList;
	}


	@Override
	public void insertBoard(BoardDto boardDto) {
		
		boardRepository.insertBoard(boardDto);
	}

	@Override
	public void updateBoard(BoardDto readBoardDto) {
		
		boardRepository.updateBoard(readBoardDto);
	}
	
	@Override
	public void deleteBoard(Integer boardNo) {

		boardRepository.deleteBoard(boardNo);
	}
	
	@Override
	public void updateViews(Integer boardNo) {

		boardRepository.updateViews(boardNo);
	}
	
	@Override
	public BoardDto readBoard(Integer boardNo) {
		
		BoardDto readBoardDto = boardRepository.readBoard(boardNo); 
		return readBoardDto;
	}
	
	@Override
	public List<CommentDto> selectComment(Integer boardNo) {
		List<CommentDto> commentList = boardRepository.selectComment(boardNo);
		
		return commentList;
	}

	@Override
	public void writeComment(String commentContent, EmployeeDto employeeDto, Integer boardNo) {
		
		//현재 날짜와 시간
		LocalDateTime now = LocalDateTime.now();
		String commentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		boardRepository.writeComment(commentContent, employeeDto, boardNo, commentDate);
	}

	

	

	
	
}
