package Member.Service;

import java.sql.SQLException;
import java.util.Scanner;

import Member.DAO.BoardDAO;
import Member.DTO.BoardDTO;
import Member.DTO.MemberDTO;

public class BoardService {
	// DAO와 DTO를 사용하여 부메뉴와 CRUD를 처리
	
	// 필드
	public BoardDAO boardDAO = new BoardDAO(); // 1단계, 2단계가 수행된다
	
	// 생성자
	
	// 메서드(부메뉴, 생성, 모두보기, 1개보기, 수정하기, 삭제하기)
	public MemberDTO subMenu(Scanner input, MemberDTO session) throws SQLException {
		boolean subRun = true;
		
		while (subRun) {
			System.out.println("게시판 서비스입니다.");
			System.out.println("1. 게시글 작성");
			System.out.println("2. 게시글 자세히보기");
			System.out.println("3. 게시글 전체보기");
			System.out.println("4. 게시글 수정");
			System.out.println("5. 게시글 삭제");
			System.out.println("6. 자유 게시판 메뉴로 돌아가기");
			System.out.print(">>>");
			String subSelect = input.next();
			
			switch (subSelect) {
			case "1":
				System.out.println("게시글 작성 메서드로 진입합니다.");
				insertBoard(boardDAO, input, session);
				break;
				
			case "2":
				System.out.println("게시글 자세히 보기 메서드로 진입합니다.");
				readOne(input, session);
				break;
				
			case "3":
				System.out.println("게시글 전체 보기 메서드로 진입합니다.");
				selectAll(boardDAO, session);
				break;
				
			case "4":
				System.out.println("게시글 수정 메서드로 진입합니다.");
				modify(input, session);
				break;
				
			case "5":
				System.out.println("게시글 삭제 메서드로 진입합니다.");
				deleteOne(input, session);
				break;
				
			case "6":
				System.out.println("게시판 서비스 종료. 자유 게시판 메뉴로 돌아갑니다.");
				boardDAO.connection.close(); // 게시판 종료시 connection 종료
				subRun = false;
				break;
				
			default:
				System.out.println("1~6번까지만 입력하세요.");
				break;
				
			} // switch문(부메뉴) 종료
			
		} // while문(부메뉴 반복) 종료
		
		return session;
		
	} // subMenu 메서드 종료

	private MemberDTO deleteOne(Scanner input, MemberDTO session) throws SQLException {
		// 제목을 찾아서 현재 로그인한 아이디가 작성자의 아이디와 동일해야 하고 입력한 비밀번호가 동일할 때만 게시글을 수정할 수 있다.
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		
		System.out.print("삭제하려는 게시글의 제목을 입력하세요: ");
		String selecttitle = input.next();
		
		System.out.print("본인의 게시글만 삭제 가능합니다. 비밀번호를 입력하세요: ");
		String pw = input.next();
		
		boardDAO.deleteOne(selecttitle, pw, session);
		
		return session;
		
	} // deleteOne(게시글 삭제) 메서드 종료

	private MemberDTO modify(Scanner input, MemberDTO session) throws SQLException {
		// 제목을 찾아서 현재 로그인한 아이디가 작성자의 아이디와 동일해야 하고 입력한 비밀번호가 동일할 때만 게시글을 수정할 수 있다.
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		

		System.out.print("수정하려는 게시글의 제목을 입력하세요: ");
		input.nextLine(); // 버퍼 비우기
		String title = input.nextLine();
		
		System.out.print("본인의 게시글만 수정 가능합니다. 비밀번호를 입력하세요: ");
		String pw = input.next();
		
		boardDAO.modify(title, pw, input, session);
		System.out.println("=========modify 메서드 종료========");
		
		return session;
	
	} // modify(게시글 수정) 메서드 종료

	private MemberDTO selectAll(BoardDAO boardDAO, MemberDTO session) throws SQLException {
		// DAO에게 전체보기 하는 서비스를 제공한다.
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId() + "입니다.");
		
		System.out.println("==========================");
		System.out.println("======게시판 목록입니다.======");
		boardDAO.selectAll();
		System.out.println("==========================");
		
		return session;
		
	} // selectAll(게시글 전체보기) 메서드 종료

	private MemberDTO readOne(Scanner input, MemberDTO session) throws SQLException {
		// 제목을 입력하면 내용이 보이도록 select 처리
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId() + "입니다.");
		
		System.out.print("보고 싶은 게시글의 제목을 입력하세요: ");
		String title = input.next();
		
		boardDAO.readOne(title, session);
		System.out.println("=======readOne 메서드 종료========");
		
		return session;
		
	} // readOne(게시글 자세히보기) 메서드 종료

	private MemberDTO insertBoard(BoardDAO boardDAO, Scanner input, MemberDTO session) throws SQLException {
		// 키보드로 입력한 데이터르 DTO를 사용하여 데이터베이스에 insert
		
		BoardDTO boardDTO = new BoardDTO(); // 빈객체 생성
		
		System.out.println("현재 로그인 되어 있는 아이디: " + session.getId());
		
		System.out.println("작성자: " + session.getId());
		boardDTO.setBwriter(session.getId());		
		
		System.out.print("제목: ");
		boardDTO.setBtitle(input.next());
		
		input.nextLine(); // 버퍼 비우기 Line 쓸 때는 비워야 오류가 나지 않음!!
		
		System.out.print("내용: ");
		boardDTO.setBcontent(input.nextLine()); // 띄어쓰기가 있는 문장은 nextLine()
		
		boardDAO.insertBoard(boardDTO, session); // 위에서 만든 객체를 DAO에게 전달
		System.out.println("============insertBoard 메서드 종료=============");
		
		return session;
		
	} // insertBoard(게시글 작성) 메서드 종료

} // class 종료
