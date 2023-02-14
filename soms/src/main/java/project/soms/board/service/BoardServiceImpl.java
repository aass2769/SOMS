package project.soms.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.soms.board.dto.BoardDto;
import project.soms.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardRepository boardRepository;
	
	@Override
	public List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput) {
		
		List<BoardDto> selectBoard = boardRepository.selectBoard(boardSection, selectList, searchInput);	
		
		//게시판 번호 정의
		int count = 0;		
		for( BoardDto selectBoards : selectBoard) {
			count += 1;
			selectBoards.setBoardNumber(selectBoard.size() - count);
		}

		return selectBoard;
	}

	@Override
	public Integer boardListSum(String boardSection) {

		Integer boardListSum = boardRepository.boardListSum(boardSection);
		return boardListSum;
	}

}
