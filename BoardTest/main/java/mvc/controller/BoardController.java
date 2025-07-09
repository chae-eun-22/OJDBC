package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LISTCOUNT = 5;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		if (command.equals("/BoardListAction.do")) { // 등록된 글 목록 페이지 출력하기
			requestBoardList(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/list.jsp");
			rd.forward(request, response);
		} else if (command.equals("/BoardWriteForm.do")) { // 글 등록 페이지 출력
		 requestLoginName(request);
		 RequestDispatcher rd = request.getRequestDispatcher("./board/writeForm.jsp");
		 rd.forward(request, response);
		 } else if (command.equals("/BoardWriteAction.do")) { // 새로운 글 등록
			requestBoardWrite(request);
			response.sendRedirect("BoardListAction.do");
			
			// 선택된 글 상자 페이지 가져오기
		} else if (command.equals("/BoardViewAction.do")) {
			requestBoardView(request);
			RequestDispatcher rd = request.getRequestDispatcher("/BoardView.do");
			rd.forward(request, response);
		} else if (command.equals("/BoardView.do")) { // 글 상세 페이지 출력하기
			RequestDispatcher rd = request.getRequestDispatcher("./board/view.jsp");
			rd.forward(request, response);
		} else if (command.equals("/BoardUpdateAction.do")) { // 선택된 글 수정하기
			requestBoardUpdate(request);
			response.sendRedirect("BoardListAction.do");
		} else if (command.equals("/BoardDeleteAction.do")) { // 선택된 글 삭제하기
			requestBoardDelete(request);
			response.sendRedirect("BoardListAction.do");
		} else if (command.equals("/BoardListAction.do")) { // 검색어 입력하기
			requestBoardSearch(request);
			RequestDispatcher rd = request.getRequestDispatcher("./board/list.jsp");
			rd.forward(request, response);
		}
	}

	// 등록된 글 목록 가져오기
	private void requestBoardList(HttpServletRequest request) {
		BoardDAO dao = BoardDAO.getInstance();
		ArrayList<BoardDTO> boardlist = new ArrayList<BoardDTO>();
		
		int pageNum = 1;
		int limit = LISTCOUNT;
		
		// 현재 페이지 가져오기
		if (request.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		// 검색 파라미터
		String items = request.getParameter("items");
		String text = request.getParameter("text");
		
		// 검색 여부 판단 후 JSP에 넘기기
		boolean isSearch = (items != null && !items.isEmpty()) && (text != null && !text.isEmpty());
		request.setAttribute("isSearch", Boolean.valueOf(isSearch));
		
		// 전체 레코드 수
		int total_record = dao.getListCount(items, text);
		
		// 게시글 목록 가져오기
		boardlist = dao.getBoardList(pageNum, limit, items, text);
		
		// 전체 페이지 수 계산
		int total_page = (total_record + limit - 1) / limit; // 올림 처리
		
		// JSP에 넘길 데이터 설정
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("total_page", total_page);
		request.setAttribute("total_record", total_record);
		request.setAttribute("boardList", boardlist);
		request.setAttribute("items", items); // 검색 항목 유지용
		request.setAttribute("text", text); // 검색어 유지용
	}
	
	 // 인증된 사용자명 가져오기
	
	 public void requestLoginName(HttpServletRequest request){
//	String id = request.getParameter("id"); // id 잘 넘어오나 확인
	 BoardDAO dao = BoardDAO.getInstance();
		/* String name = dao.getLoginNameById(id); */
	 // dao에서 name을 가져옴
	 
//	 request.setAttribute("name", name);
	 }
	 
	
	// 새로운 글 등록하기
	public void requestBoardWrite(HttpServletRequest request) {
		BoardDAO dao = BoardDAO.getInstance();
		
		BoardDTO board = new BoardDTO();
		/* board.setId(request.getParameter("id")); */
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("subject"));
		System.out.println(request.getParameter("content"));
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
		String regist_day = formatter.format(new java.util.Date());
		
		board.setHit(0);
		board.setRegist_day(regist_day);
		board.setIp(request.getRemoteAddr());
		
		dao.insertBoard(board);
	}
	
	// 선택된 글 상세 페이지 가져오기
	public void requestBoardView(HttpServletRequest request) {
		BoardDAO dao = BoardDAO.getInstance();
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDTO board = new BoardDTO();
		board = dao.getBoardByNum(num, pageNum);
		
		request.setAttribute("num", num);
		request.setAttribute("page", pageNum);
		request.setAttribute("board", board);
	}
	
	// 선택된 글 내용 수정하기
	public void requestBoardUpdate(HttpServletRequest request) {
		BoardDAO dao = BoardDAO.getInstance();
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDTO board = new BoardDTO();
		board.setNum(num);
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
		String regist_day = formatter.format(new java.util.Date());
		
		board.setHit(0);
		board.setRegist_day(regist_day);
		board.setIp(request.getRemoteAddr());
		
		dao.updateBoard(board);
	}
	
	// 선택된 글 삭제하기
	public void requestBoardDelete(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		BoardDAO dao = BoardDAO.getInstance();
		dao.deleteBoard(num);
	}
	
	// 검색어 입력하기
	private void requestBoardSearch(HttpServletRequest request) {
		String items = request.getParameter("items");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		ArrayList<BoardDTO> searchList = dao.searchBoard(items, keyword);
		request.setAttribute("boardList", searchList);
	}
}
