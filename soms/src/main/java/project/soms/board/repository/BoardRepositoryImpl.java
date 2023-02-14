package project.soms.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.soms.board.dto.BoardDto;
import project.soms.board.repository.mapper.BoardMapper;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

	private final BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput) {
		
		List<BoardDto> selectBoard = boardMapper.selectBoard(boardSection, selectList, searchInput);
		return selectBoard;
	}

	@Override
	public Integer boardListSum(String boardSection) {
		
		Integer boardListSum = boardMapper.boardListSum(boardSection);
		return boardListSum;
		
	}
}
