package project.soms.board.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.soms.board.dto.BoardDto;

@Mapper
public interface BoardMapper {

	List<BoardDto> selectBoard(@Param("boardSection") String boardSection, @Param("selectList") String selectList, @Param("searchInput") String searchInput);
	
	Integer boardListSum(String boardSection);
	}
