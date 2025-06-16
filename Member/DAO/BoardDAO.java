package Member.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Member.DTO.BoardDTO;
import Member.DTO.MemberDTO;

public class BoardDAO {
	// 게시판의 db와 연동을 담당
	// 1단계: Connect 객체를 사용하여 ojdb6.jar를 생성
	// 2단계: url, id, pw, sql 쿼리문을 작성
	// 3단계: 쿼리문을 실행
	// 4단계: 쿼리문 실행 결과를 받음
	// 5단계: 연결 종료를 진행

	// 필드
	public BoardDTO boardDTO = new BoardDTO();
	public Connection connection = null; // 1단계에서 사용하는 객체
	public Statement statement = null; // 3단계에서 사용하는 객체(구형), 변수 직접 처리 '" + name "'
	public PreparedStatement preparedStatement = null; // 3단게에서 사용하는 객체(신형), ?(인파라미터)
	public ResultSet resultSet = null; // 4단계에서 결과 받는 표 객체 executeQuery(select 결과)
	public int result = 0; // 4단계에서 결과 받는 정수 execouteUpdate(insert, update, delete)
	// 1개의 행이 삽입 | 수정 | 삭제되었습니다. (정상처리 -> commit)
	// 0개의 행이 삽입 | 수정 | 삭제되었습니다. (비정상처리 -> rollback)
	
	// 기본 생성자
	public BoardDAO() {
		
		try { // 예외가 발생할 수 있는 실행문
			// 프로그램 강제 종료 처리용
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계 ojdbc6.jar 호출
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.153:1521:xe", "test", "test"); // 2단계
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("드라이버 이름이나, ojdbc6.jar 파일이 잘못되었습니다.");
			e.printStackTrace(); // 빨간색 오류
			System.exit(0); // 강제 종료
			
		} catch (SQLException e) {
			
			System.out.println("url, id, pw가 잘못되었습니다. BoardDAO에 기본 생성자를 확인하세요.");
			e.printStackTrace(); // 빨간색 오류
			System.exit(0); // 강제 종료
			
		}
		
	} // 기본 생성자 종료
	
	
	// 메서드
	public MemberDTO insertBoard(BoardDTO boardDTO, MemberDTO session) throws SQLException {
		// jdbc를 이용하여 insert 쿼리를 처리
		// PreparedStatement문을 사용해보자
		// 동적 쿼리문이라고 하고 ?를 사용해서 세터로 입력
		
		try {
			
			String sql = "insert into board(bno, btitle, bcontent, bwriter, bdate) "
					+ " values(board_seq.nextval, ?, ?, ?, sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle()); // 첫번째 ?에 DTO 객체에 있는 제목을 넣음
			preparedStatement.setString(2, boardDTO.getBcontent()); // 두번째 ?에 DTO 객체 내용 넣음
			preparedStatement.setString(3, boardDTO.getBwriter()); // 세번째 ?에 DTO 객체 아이디 넣음
			
			
			result = preparedStatement.executeUpdate(); // 쿼리문 실행해서 결과를 정수로 받음
			// result = preparedStatement.executeUpdate(sql); 오류 발생
			
			if (result > 0) {
				
				System.out.println(result + "개의 게시물이 등록되었습니다.");
				connection.commit(); // 영구 저장
				
			} else {
				
				System.out.println("쿼리 실행 결과: " + result);
				System.out.println("게시물 등록에 실패하셨습니다.");
				connection.rollback(); // 롤백(저장 취소)
				
			} // if문 종료
			
		} catch (SQLException e) {
			
			System.out.println("예외 발생: insertBoard() 메서드에 쿼리문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류
			
		} finally { // 예외 발생 및 저장 실행 후 무조건 처리되는 실행문
			
			preparedStatement.close();
			
		}
		
		return session;
		
	} // insertBoard(게시글 작성) 메서드 종료

	public MemberDTO readOne(String title, MemberDTO session) throws SQLException {
		// 제목 문자열이 넘어온 것을 select 처리하여 출력
		
		try {
			
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board where btitle=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, title); // service에서 넘어온 찾고 싶은 제목이 ?로 넘어간다
			resultSet = preparedStatement.executeQuery(); // 쿼리문 실행 후 결과를 표로 받는다
			
			if (resultSet.next()) { // 검색 결과가 있으면
				
				BoardDTO boardDTO = new BoardDTO(); // 빈 객체 생성
				
				boardDTO.setBno(resultSet.getInt("bno"));
				boardDTO.setBtitle(resultSet.getString("btitle"));
				boardDTO.setBcontent(resultSet.getString("bcontent"));
				boardDTO.setBwriter(resultSet.getString("bwriter"));
				boardDTO.setBdate(resultSet.getDate("bdate"));
				// 데이터 베이스에 있는 행을 객체로 넣기 완료
				
				System.out.println("================================");
				System.out.println("번호: " + boardDTO.getBno());
				System.out.println("제목: " + boardDTO.getBtitle());
				System.out.println("내용: " + boardDTO.getBcontent());
				System.out.println("작성자: " + boardDTO.getBwriter());
				System.out.println("작성일: " + boardDTO.getBdate());
				
			} else { // 검색 결과가 없으면
				
				System.out.println("해당하는 게시물이 존재하지 않습니다.");
				
			} // if문 종료
			
		} catch (SQLException e) {
			
			System.out.println("예외 발생: readOne() 메서드를 확인하세요.");
			e.printStackTrace(); // 빨간색 오류
			
		} finally {
			
			resultSet.close();
			preparedStatement.close();
			
		}
		
		return session;
		
	} // readOne(게시글 자세히보기) 메서드 종료

	public void selectAll() throws SQLException { // throws SQLException 쿼리문 예외 처리용
		// SQL를 사용하여 전체 목록 보기 결과 출력
		
		try {
			
			String sql = "select bno, btitle, bwriter, bdate from board order by bdate desc";
			// 데이터베이스에 board 테이블 내용을 가져오는 쿼리문
			
			statement = connection.createStatement(); // 쿼리문을 실행 객체 생성
			resultSet = statement.executeQuery(sql); // 쿼리문을 실행하여 결과를 표로 받는다.
			
			System.out.println("번호\t 제목\t\t 아이디\t\t 작성일");
			
			while (resultSet.next()) { // 결과 표에 위에서부터 아래까지 내려오면서 출력
				
				System.out.print(resultSet.getInt("bno") + "\t");
				System.out.print(resultSet.getString("btitle") + "\t\t");
				System.out.print(resultSet.getString("bwriter") + "\t");
				System.out.println(resultSet.getDate("bdate") + "\t");
				
			}
			
			System.out.println("=============끝==============");
			
		} catch (SQLException e) {
			
			System.out.println("selectAll() 메서드에 쿼리문이 잘못되었습니다.");
			e.printStackTrace(); // 빨간색 오류
			
		} finally {
			
			resultSet.close();
			statement.close();
			// 열린 객체를 닫아야 다른 메서드도 정상 작동함
			
		}
		
	} // selectAll(게시글 전체보기) 메서드 종료

	public MemberDTO modify(String title, String pw, Scanner input, MemberDTO session) throws SQLException {
		// 제목을 찾아서 현재 로그인한 아이디가 작성자의 아이디와 동일해야 하고 입력한 비밀번호가 동일할 때만 게시글을 수정할 수 있다.
		
		BoardDTO boardDTO = new BoardDTO();
		
		System.out.println("[수정할 내용을 입력하세요]");
		System.out.print("제목: ");
		boardDTO.setBtitle(input.next());
		
		input.nextLine(); // 버퍼 비우기
		
		System.out.print("내용: ");
		boardDTO.setBcontent(input.nextLine());
		
		try {
			
			String sql = "UPDATE board SET btitle=?, bcontent=?, bdate=sysdate " + "WHERE btitle=? AND EXISTS ("
					+ "SELECT 1 FROM member WHERE id = bwriter and pw=? and id=?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getBtitle());
			preparedStatement.setString(2, boardDTO.getBcontent());
			preparedStatement.setString(3, title);
			preparedStatement.setString(4, pw);
			preparedStatement.setString(5, session.getId());
			
			result = preparedStatement.executeUpdate(); // 쿼리문 실행 후 결과를 정수로 보냄
			
			if (result > 0) {
				
				System.out.println(session.getName() + "님 " + result + "개의 게시글이 수정되었습니다.");
				connection.commit(); // 영구 저장
				
			} else {
				
				System.out.println(session.getName() + "님 본인의 게시글이 아니거나 비밀번호가 틀렸으므로 게시글 수정이 되지 않았습니다.");
				connection.rollback();
				
			} // if문 종료
			
		} catch (SQLException e) {
			
			System.out.println("예외 발생: modify() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류
			
		} finally {
			
			preparedStatement.close();
			
		}
		
		return session;
		
	} // modify(게시글 수정) 메서드 종료

	public MemberDTO deleteOne(String selecttitle, String pw, MemberDTO session) throws SQLException {
		// 서비스에서 받은 게시물의 제목을 이용하여 현재 로그인한 아이디가 작성자의 아이디와 동일해야 하고 입력한 비밀번호가 동일할 때만 게시글을 삭제할 수 있다.
		
		try {
			
			String sql = "delete from board where btitle=? AND EXISTS ("
					+ "SELECT 1 FROM member WHERE id = bwriter and pw=? and id=?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, selecttitle);
			preparedStatement.setString(2, pw);
			preparedStatement.setString(3, session.getId());
			
			result = preparedStatement.executeUpdate(); // 쿼리문 실행 후 결과를 정수로 리턴
			
			if (result > 0) {
				
				System.out.println(session.getName() + "님 " + result + "개의 게시글이 삭제되었습니다.");
				connection.rollback();
				
			} else {
				
				System.out.println(session.getName() + "님 본인의 게시글이 아니거나 비밀번호가 틀렸으므로 게시글이 삭제되지 않았습니다.");
				connection.rollback();
				
			} // if문 종료
			
		} catch (SQLException e) {
			
			System.out.println("예외 발생: deleteOne() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류
			
		} finally {
			
			preparedStatement.close();
			
		}
		
		return session;
		
	} // deleteOne(게시글 삭제) 메서드 종료

} // class 종료
