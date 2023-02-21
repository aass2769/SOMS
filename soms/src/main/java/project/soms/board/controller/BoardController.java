package project.soms.board.controller;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.soms.board.dto.BoardDto;
import project.soms.board.dto.CommentDto;
import project.soms.board.service.BoardService;
import project.soms.employee.dto.EmployeeDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService boardService;
		
	//전사 게시판,부서 게시판에 게시글 리스트 SELECT하는 메서드
	//검색 하는 메서드
	@GetMapping("boardList") // board/main
	public String selectBoard(Model model, BoardDto boardDto,
										   HttpServletRequest request) {
		
		//카테고리에서 원하는 부서 클릭 시 url로 boardSection 파라미터를 보내줌.
		String boardSection = request.getParameter("boardSection");
		
		//전체 게시물 수
		int total = boardService.selectBoardTotal(boardSection);
		
		//  전체 게시물 수에 따른 페이지의 개수  (double) 12/10 -> ceil(1.2) ->int(2.0) -> 2
		
		int totalPage = (int) Math.ceil((double)total/boardDto.getPageLimit());
		
		// pageOffset = 페이지당 제외하고 출력해야하는 글 개수, viewPage = 페이지 개수 중 페이지
		boardDto.setPageOffset((boardDto.getViewPage() - 1) * boardDto.getPageLimit());
		
		//전체 게시글 수가 45개이고 10개씩 출력한다고 가정하면
		//1page -> 45 ~ 36 2page -> 35 ~ 26 ....
		// 1page의 startRowNo(시작 행 넘버) = total(총게시물 수) - (viewPage(현재페이지) - 1) * 10(출력할 개시글 수) 
		int startRowNo = total - (boardDto.getViewPage() - 1) * boardDto.getPageLimit();
		
		//공지 없는 리스트
		List<BoardDto> boardList = new ArrayList<>();
		boardList =	boardService.selectBoard(boardSection, boardDto);
		
		//공지 있는 리스트
		List<BoardDto> noticeBoardList = new ArrayList<>();
		noticeBoardList = boardService.selectNoticeBoard(boardSection, boardDto);
		
		model.addAttribute("total", total);
		model.addAttribute("viewPage", boardDto.getViewPage());
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startRowNo", startRowNo);
		model.addAttribute("boardList" , boardList);
		model.addAttribute("boardSection", request.getParameter("boardSection"));
		model.addAttribute("noticeBoardList", noticeBoardList);
		model.addAttribute("pageLimit", boardDto.getPageLimit());
		return "board/board";
	}

	//게시판 작성 페이지 불러오는 메서드
	@GetMapping("boardWrite")
	public String writeBoard(Model model, String boardSection, HttpServletRequest request) {
		
		BoardDto readBoardDto = new BoardDto();
		
		if(request.getParameter("boardNo") != null) {
			Integer boardNo = Integer.parseInt(request.getParameter("boardNo"));
			//게시글 상세내용 불러오는 메서드
			readBoardDto = boardService.readBoard(boardNo);
		}
		
		model.addAttribute("readBoardDto", readBoardDto);
		model.addAttribute("employee", loginEmployee(request));
		model.addAttribute("boardSection", boardSection);
		
		return "board/writeBoard";
	}
	
	
	//게시판 작성하는 post 메서드
	@PostMapping("boardWrite")
	public String writeBoard(Model model, String boardSection, BoardDto boardDto, HttpServletRequest request, RedirectAttributes redirectAttributes, BoardDto readBoardDto) {

		if(boardDto.getBoardAnnouncement() == null) {
			boardDto.setBoardAnnouncement("공지없음");
		}
		//INSERT 메서드 실행
		boardService.insertBoard(boardDto);
		
		//boardList 메서드로 redirect 할 때 boardSection값을 보내줌.
		redirectAttributes.addAttribute("boardSection", boardSection);
		return "redirect:/board/boardList";
	}
	
	//응답이 틀리면 http 메서드 오류가 발생할 수 있음 (클라이언트오류 405ERROR)
	//게시판 수정 메서드
	@PostMapping("boardUpdate")
	public String updateBoard(Model model, BoardDto readBoardDto, RedirectAttributes redirect) {
		
		if(readBoardDto.getBoardAnnouncement() == null) {
			readBoardDto.setBoardAnnouncement("공지없음");
		}
		
		boardService.updateBoard(readBoardDto);

		redirect.addAttribute("boardNo",readBoardDto.getBoardNo());
		
		return "redirect:/board/boardRead";
	}
	
	
	//세션 가져오는 메서드
	public EmployeeDto loginEmployee(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session == null) {
			System.out.println("세션이 없습니다.");
		}
		
		EmployeeDto employeeDto = (EmployeeDto) session.getAttribute("LOGIN_EMPLOYEE");
		return employeeDto;
	}
	
	//게시물 상세내용과 댓글 가져오는 메서드
	@GetMapping("boardRead")
	public String readBoard(Model model, Integer boardNo, HttpServletRequest request) {
		//given
		boardService.updateViews(boardNo);		//조회 수 update
		BoardDto readBoardDto = boardService.readBoard(boardNo);	//게시글 상세내용
		List<CommentDto> commentList = boardService.selectComment(boardNo);	//댓글
		
		//add model
		model.addAttribute("employee", loginEmployee(request)); // 세션
		model.addAttribute("readBoardDto", readBoardDto);
		model.addAttribute("commentList", commentList);
		
		//return
		return "board/readBoard";
	}
	
	//댓글 작성하는 메서드
	@PostMapping("commentWrite")
	public String writeComment(HttpServletRequest request, String commentContent, Integer boardNo, RedirectAttributes redirectAttributes) {
		
		EmployeeDto employeeDto = loginEmployee(request);
		boardService.writeComment(commentContent, employeeDto, boardNo);
		redirectAttributes.addAttribute("boardNo", boardNo);
		
		return "redirect:/board/boardRead";
	}
	
	//게시글 삭제 메서드
	@PostMapping("boardDelete")
	public String deleteBoard(Integer boardNo, String boardSection, RedirectAttributes redirectAttributes) {

		boardService.deleteBoard(boardNo);
		
		redirectAttributes.addAttribute("boardSection", boardSection);
		return "redirect:/board/boardList";
	}
	
}
