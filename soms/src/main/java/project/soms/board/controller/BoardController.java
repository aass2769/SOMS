package project.soms.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("total")
	public String total() {
		
		return "board/totalBoard";
	}
	
	@GetMapping("team")
	public String team() {
		
		return "board/teamBoard";
	}
	
	
	@GetMapping("anonymous")
	public String anonymous() {
		
		return "board/anonymousBoard";
	}
}
