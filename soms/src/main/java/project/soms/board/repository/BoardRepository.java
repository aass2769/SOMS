package project.soms.board.repository;

import java.util.List;

import project.soms.board.dto.BoardDto;

public interface BoardRepository{

	List<BoardDto> selectBoard(String boardSection, String selectList, String searchInput);
	
	Integer boardListSum(String boardSection);

}
