package project.soms.board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import project.soms.board.repository.BoardRepository;
import project.soms.board.repository.BoardRepositoryImpl;
import project.soms.board.repository.mapper.BoardMapper;
import project.soms.board.service.BoardService;
import project.soms.board.service.BoardServiceImpl;

@Configuration
@RequiredArgsConstructor
public class BoardConfiguration {
	
	private final BoardMapper boardMapper;
	
	@Bean
	public BoardRepository boardRepository() {
		return new BoardRepositoryImpl(boardMapper);
	}
	
	@Bean
	public BoardService boardService() {
		return new BoardServiceImpl(boardRepository());
	}
}
