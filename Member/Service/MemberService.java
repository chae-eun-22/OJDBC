package Member.Service;

import java.sql.SQLException;
import java.util.Scanner;

import Member.DAO.MemberDAO;
import Member.DTO.MemberDTO;

public class MemberService {
	// DAO와 DTO를 사용하여 부메뉴와 CRUD를 처리

	// 필드
	public MemberDAO memberDAO = new MemberDAO(); // 1단계, 2단계가 수행

	// 생성자

	// 메서드 (부메뉴, 회원가입, 로그인, 모든 회원정보 보기, 개인 회원정보 보기, 회원정보 수정, 회원정보 삭제
	public MemberDTO subMenu(Scanner input, MemberDTO session) throws SQLException {
		boolean subRun = true;

		while (subRun) {
			System.out.println("회원 서비스 메뉴입니다.");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 회원정보 보기");
			System.out.println("4. 회원정보 수정");
			System.out.println("5. 회원 탈퇴");
			System.out.println("6. 자유 게시판 메뉴로 돌아가기");
			System.out.print(">>>");
			String subSelect = input.next();
			
			switch (subSelect) {
			case "1":
				System.out.println("회원가입 메서드로 진입합니다.");
				sign(memberDAO, input, session);
				break;
				
			case "2":
				System.out.println("로그인 메서드로 진입합니다.");
				session = login(memberDAO, input, session);
				break;
				
			case "3":
				System.out.println("회원정보 보기 메서드로 진입합니다.");
				readOne(memberDAO, input, session);
				break;
				
			case "4":
				System.out.println("회원정보 수정 메서드로 진입합니다.");
				modify(input, session);
				break;
				
			case "5":
				System.out.println("회원 탈퇴 메서드로 진입합니다.");
				deleteOne(input, session);
				break;
				
			case "6":
				System.out.println("회원 서비스 종료. 자유 게시판 메뉴로 돌아갑니다.");
				memberDAO.connection.close(); // 회원 서비스 종료시 connection 종료
				subRun = false;
				break;
				
			case "99":
				System.out.println("히든 번호를 입력하셨습니다. 모든 회원정보 보기 메서드로 진입합니다.");
				selectAll(memberDAO, session);
				break;
				
			default:
				System.out.println("1~6번까지만 입력하세요.");
				break;
				
			} // switch문(부메뉴) 종료
			
		} // while문(부메뉴) 종료

		return session;
		
	} // subMenu 메서드 종료

	private MemberDTO selectAll(MemberDAO memberDAO, MemberDTO session) throws SQLException {
		// DAO에게 전체보기 하는 서비스를 제공한다. (선택지에 없는 히든 창)
		
		System.out.println("===================================");
		System.out.println("==========모든 회원 목록입니다.=========");
		memberDAO.selectAll(session);
		System.out.println("===================================");
		
		return session;
	}

	private MemberDTO deleteOne(Scanner input, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 회원 탈퇴를 진행한다
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		
		System.out.print("탈퇴하려는 회원의 아이디를 입력하세요: ");
		String selectid = input.next();
		
		System.out.print("다시는 되돌릴 수 없습니다. 정말로 탈퇴하시겠습니까? Yes/No: ");
		String yn = input.next();
		
		if (yn.equalsIgnoreCase("yes")) {
			
			session.getPw();
			
			System.out.print("정말로 탈퇴하려면 현재 비밀번호를 입력하세요: ");
			String selectpw = input.next();
			
			memberDAO.deleteOne(selectid, selectpw, session);
			System.out.println("==========회원정보 수정 메서드 종료=========");
			
		} else {
			
			System.out.println("회원 서비스 메뉴로 돌아갑니다.");
			System.out.println("========================");
			
		}
		
		return session;
		
	}

	private MemberDTO modify(Scanner input, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 회원정보를 수정한다
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		
		System.out.print("비밀번호를 입력하세요: ");
		String pw = input.next();
		
		memberDAO.modify(pw, input, session);
		System.out.println("==========회원정보 수정 메서드 종료=========");
		
		return session;
		
	} // modify(내 회원정보 수정) 메서드 종료

	private MemberDTO readOne(MemberDAO memberDAO, Scanner input, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 내 회원정보를 본다
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		
		System.out.print("비밀번호를 입력하세요: ");
		String pw = input.next();
		
		memberDAO.readOne(pw, session);
		System.out.println("=======회원정보 보기 메서드 종료======");
		
		return session;
		
	} // readOne(회원정보 보기) 메서드 종료

	private MemberDTO login(MemberDAO memberDAO, Scanner input, MemberDTO session) throws SQLException {
		// 회원가입한 아이디와 비밀번호로 로그인한다.
		
		session = new MemberDTO();
		
		System.out.print("아이디를 입력하세요: ");
		String id = input.next();
		session.setId(id);
		
		System.out.print("비밀번호를 입력하세요: ");
		String pw = input.next();
		session.setPw(pw);
		
		session = memberDAO.login(session);
		System.out.println("==========로그인 메서드 종료==========");
		
		return session;
		
	} // login(로그인) 메서드 종료

	private MemberDTO sign(MemberDAO memberDAO, Scanner input, MemberDTO session) throws SQLException {
		// 키보드로 입력한 데이터를 DTO를 사용하여 데이터베이스에 insert
		
		MemberDTO memberDTO = new MemberDTO(); // 빈객체 생성
		
		System.out.println("회원가입을 진행합니다.");
		
		System.out.print("이름을 입력하세요: ");
		memberDTO.setName(input.next());
		
		System.out.print("아이디를 입력하세요: ");
		memberDTO.setId(input.next());
		
		System.out.print("비밀번호를 입력하세요: ");
		memberDTO.setPw(input.next());
		
		memberDAO.sign(memberDTO); // 위에서 만든 객체를 DAO에게 전달
		System.out.println("=======회원 가입 메서드 종료========");
		
		return session;
		
	} // sign(회원가입) 메서드 종료

} // class 종료
