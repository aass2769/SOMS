package project.soms.board.service;

import java.util.List;

import project.soms.board.dto.BoardDto;

public interface BoardService {

	List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput);
	
	Integer boardListSum(String boardSection);
}
