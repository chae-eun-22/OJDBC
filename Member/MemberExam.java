package Member;

import java.sql.SQLException;
import java.util.Scanner;

import Member.Service.BoardService;
import Member.Service.MemberService;
import Member.DTO.MemberDTO;

public class MemberExam {
	// 필드
	public static Scanner input = new Scanner(System.in);
	static MemberDTO session = null;
	
	// 생성자 -> static 블럭
	
	
	// 메서드
	public static void main(String[] args) throws SQLException {
		// DTO: 객체를 담당
		// DAO: 데이터베이스에서 연동을 담당
		// service: 부메뉴와 서비스를 담당
		boolean run = true;
		
		while (run) {
			System.out.println("자유 게시판입니다.");
			System.out.println("1. 회원");
			System.out.println("2. 게시판");
			System.out.println("3. 종료");
			System.out.print(">>>");
			String select = input.next();
			
			switch (select) {
			case "1":
				System.out.println("회원 서비스로 진입합니다.");
				MemberService memberService = new MemberService();
				session = memberService.subMenu(input, session);
				break;
				
			case "2":
				System.out.println("게시판 서비스로 진입합니다.");
				BoardService boardService = new BoardService();
				boardService.subMenu(input, session);
				break;
				
			case "3":
				System.out.println("자유 게시판 프로그램을 종료합니다.");
				run = false;
				break;
				
			default:
				System.out.println("1~3번까지만 입력하세요.");
				break;
				
			} // switch문 종료
			
		} // while문 종료

	} // main 메서드 종료

} // class 종료
