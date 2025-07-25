package filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

public class LogFileFilter implements Filter { // jakarta.servlet.Filter
	// 웹 서버에 접속하는 요청에 대한 로그를 파일로 저장하려 한다.
	// 0순위로 처리되는 필터 인터페이스를 사용하는 구현체 implements로 구현함
	
	PrintWriter writer; // 파일 저장용 객체

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 필터 시작시 초기화시키는 메서드
		System.out.println("BookMarket 필터 초기화...");
		String filename = filterConfig.getInitParameter("filename");
		// web.xml에 파일 경로를 알아온다. <param-value>c:\\logs\\bookmarket.log</param-value>
		
		if (filename == null) {
			throw new ServletException("로그 파일의 이름을 찾을 수 없습니다.");
		}
		
		try {
			// 파일이 존재하면 파일 이름과 저장할 수 있는 객체를 생성하여 writer에 넣는다.
			writer = new PrintWriter(new FileWriter(filename, true), true);
			//								import java.io.FileWriter;
			//														true 같은 파일명 자료에 데이터 추가
		} catch (IOException e) {
			throw new ServletException("로그 파일을 열 수 없습니다.");
		}
	}

	@Override // 재정의 서블릿에서 제공하는 메서드를 내가 수정해서 사용함
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		// 요청과 응답에 대한 로그 기록
		writer.println("접속한 클라이언트 IP: " + request.getRemoteAddr());
		long start = System.currentTimeMillis(); // 시작 시간
		writer.println("접근한 URL 경로(커스텀): " + getURLPath(request));
		// 요청 받은 주소가 내가 보고싶은 메서드로 변환되어 콘솔 출력
		writer.println("요청 처리 시작 시각(커스텀): " + getCurrentTime());
		
		chain.doFilter(request, response); // 필터에게 요청과 응답에 대한 자료를 전송함
		
		long end = System.currentTimeMillis(); // 종료 시간 저장용
		writer.println("요청 처리 종료 시각(커스텀): " + getCurrentTime());
		writer.println("요청 처리 소요 시간(종료-시작): " + (end-start) + "ms");
		writer.println("========================필터 종료=========================");
	}

	@Override
	public void destroy() {
		// 필터 종료전에 처리되는 메서드
		System.out.println("BookMarket 필터 종료중...");
		writer.close(); // 파일도 열었으면 닫아줘야 한다.
	}

	// 로그 시간과 종료등을 기록하는 메서드 추가 p436
	private String getURLPath(ServletRequest request) {
		HttpServletRequest req; // 빈객체 생성
		String currentPath = ""; // 사용중인 경로
		String queryString = ""; // 접속 경로 url

		if (request instanceof HttpServletRequest) {
			// request instanceof HttpServletRequest 같은 객체인지 비교용
			req = (HttpServletRequest) request; // 강제타입 변환으로 객체 삽입
			currentPath = req.getRequestURI(); // 전달받은 객체를 생성한 객체에 넣음
			queryString = req.getQueryString(); // 전달받은 객체를 생성한 객체에 넣음
			queryString = queryString == null ? "" : "?" + queryString; // 3항 연산자로 비교
			// http://192.168.111.101:8080/books.jsp
			// http://192.168.111.101:8080/book.jsp?id=ISBN1234
		}
		return currentPath + queryString;
	}
	
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(); // 달력 객체 생성
		calendar.setTimeInMillis(System.currentTimeMillis()); // 달력에 초까지 생성
		return formatter.format(calendar.getTime()); // 날짜 정보를 리턴한다.
	}

}
