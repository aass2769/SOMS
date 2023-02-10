package project.soms.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("totalBoard")
	public String total() {
		
		return "board/totalBoard";
	}
	
	@GetMapping("teamBoard")
	public String team() {
		
		return "board/teamBoard";
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
