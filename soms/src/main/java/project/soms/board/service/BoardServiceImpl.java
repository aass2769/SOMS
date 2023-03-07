package project.soms.board.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
	
	//공지없음 게시글 목록 SELECT
	@Override
	public List<BoardDto> selectBoard(String boardSection, BoardDto boardDto) {
		
		List<BoardDto> boardList = boardRepository.selectBoard(boardSection, boardDto);
		
		transDate(boardList);
		
		return boardList;
	}
	
	//공지있음 게시글 목록 SELECT
	@Override
	public List<BoardDto> selectNoticeBoard(String boardSection, BoardDto boardDto) {

		List<BoardDto> noticeBoardList = boardRepository.selectNoticeBoard(boardSection, boardDto);
		
		transDate(noticeBoardList);
		
		return noticeBoardList;
	}
	
	//오늘 날짜 이전이면 yyyy-MM-dd형식 오늘 날짜면 yyyy-MM-dd HH:mm:ss로 출력해주는 메서드
	public void transDate(List<BoardDto> boardList) {
		
		//오늘 날짜 선언, LocalDate는 오늘 날짜만 나옴
		LocalDate today = LocalDate.now();
		
		//가져오는 boardList의 사이즈 만큼 반복.
		//만약 boardDateOnly가 오늘날짜 이전이면 yyyy-mm-dd형식으로 리스트에 set함. 이전날짜가 아닌 오늘날짜면 포멧하지 않고 list에 set
		for(int i = 0; i < boardList.size(); i++) {
			
			//boardDate는 LocalDateTime(yyyy-MM-dd HH:mm:ss)타입이기 떄문에 LocalDateTime으로 선언
			LocalDateTime boardDate = LocalDateTime.parse(boardList.get(i).getBoardDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			//boardList의 boardDate 값에서 시간 정보를 제외하고 날짜 정보만 추출하기 위해 boardDateOnly 선언.
			LocalDate boardDateOnly = boardDate.toLocalDate();
			
			//LocalDate(날짜)객체인 boardDateOnly와 today를 비교. boardDateOnly가 today의 이전 날짜인지.
			if(boardDateOnly.isBefore(today)) {
				String formattedDate = boardDateOnly.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				boardList.get(i).setBoardDate(formattedDate);
			}else {
				boardList.get(i).getBoardDate();
			}
		}
	}

	//총 게시물 수 select 메서드
	@Override
	public int selectBoardTotal(String boardSection) {
		
		int total = boardRepository.selectBoardTotal(boardSection);
		return total;
	}

	//게시글 insert 메서드
	@Override
	public void insertBoard(BoardDto boardDto) {
		
		boardRepository.insertBoard(boardDto);
	}

	//게시글 update 메서드
	@Override
	public void updateBoard(BoardDto readBoardDto) {
		
		boardRepository.updateBoard(readBoardDto);
	}
	
	//게시글 delete 메서드
	@Override
	public void deleteBoard(Integer boardNo) {

		boardRepository.deleteBoard(boardNo);
	}
	
	//조회 수 update 메서드
	@Override
	public void updateViews(Integer boardNo) {

		boardRepository.updateViews(boardNo);
	}
	
	//게시글 상세보기 select 메서드
	@Override
	public BoardDto readBoard(Integer boardNo) throws NullPointerException {
		
		BoardDto readBoardDto = boardRepository.readBoard(boardNo); 
		if (readBoardDto.getBoardTitle() == null) {
			throw new NullPointerException();
		}
		return readBoardDto;
	}
	
	//댓글 select 메서드
	@Override
	public List<CommentDto> selectComment(Integer boardNo) {
		List<CommentDto> commentList = boardRepository.selectComment(boardNo);
		
		return commentList;
	}

	//댓글 insert 메서드
	@Override
	public void writeComment(String commentContent, EmployeeDto employeeDto, Integer boardNo) {
		
		//현재 날짜와 시간
		LocalDateTime now = LocalDateTime.now();
		String commentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		boardRepository.writeComment(commentContent, employeeDto, boardNo, commentDate);
	}

	//댓글 delete 메서드
	@Override
	public void deleteComment(CommentDto commentDto) {
		boardRepository.deleteComment(commentDto);
		
	}
		
	//게시판 이전글 다음글 메서드
	@Override
	public BoardDto readBoardMove(String boardSection, Integer boardNo){
			
		BoardDto boardPage = boardRepository.readBoardMove(boardSection, boardNo);
			
		return boardPage;
	}

	

	

	
	
}
