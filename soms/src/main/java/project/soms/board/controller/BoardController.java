package project.soms.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import project.soms.board.dto.BoardDto;
import project.soms.board.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService boardService;
		
	//전사 게시판,부서 게시판에 게시글 리스트 SELECT하는 메서드
	//검색 하는 메서드
	@GetMapping("boardList") // board/main
	public String selectBoard(Model model, String boardSection,
											@RequestParam(defaultValue="") String selectList, 
											@RequestParam(defaultValue="") String searchInput) {
		
		System.out.println(selectList);
		List<BoardDto> boardList = boardService.selectBoard(boardSection, selectList, searchInput);
		Integer boardListSum = boardService.boardListSum(boardSection);
		model.addAttribute("boardList" , boardList);
		model.addAttribute("boardListSum" , boardListSum);
		return "board/board";
	}
	
	
	//각 게시판 별 검색 기능 메서드
	private String eachTeam() {
		
		return "";
	}
	
	@GetMapping("anonymousBoard")
	public String anonymous() {
		
		return "board/anonymousBoard";
	}
	
	@GetMapping("writeBoard")
	public String writeBoard() {
		
		return "board/writeBoard";
	}
	
	@GetMapping("readBoard")
	public String readBoard() {
		
		return "board/readBoard";
	}
}
