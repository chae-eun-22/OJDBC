package Member.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;

import Member.DTO.MemberDTO;

public class MemberDAO {
	// 회원의 db와 연동을 담당
	// 1단계: Connect 객체를 사용하여 ojdbc6.jar를 생성
	// 2단계: url, id, pw, sql 쿼리문을 작성
	// 3단계: 쿼리문을 실행
	// 4단계: 쿼리문 실행 결과를 받음
	// 5단계: 연결 종료를 진행

	// 필드
	public MemberDTO memberDTO = new MemberDTO();
	public Connection connection = null; // 1단계에서 사용하는 객체
	public Statement statement = null; // 3단계에서 사용하는 객체(구형), 변수 직접 처리 '" + name + "'
	public PreparedStatement preparedStatement = null; // 3단계에서 사용하는 객체(신형), ?(인파라미터)
	public ResultSet resultSet = null; // 4단계에서 결과 받는 표 객체 executeQuery(selet 결과)
	public int result = 0; // 4단계에서 결과 받는 정수 executeUpdate(insert, update, delete)
	// 1개의 행이 삽입 | 수정 | 삭제되었습니다. (정상처리 -> commit)
	// 0개의 행이 삽입 | 수정 | 삭제되었습니다. (비정상처리 -> rollback)

	// 기본 생성자
	public MemberDAO() {

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
	public void sign(MemberDTO memberDTO) throws SQLException {
		// 회원가입

		try {

			String sql = "insert into member(mno, name, id, pw, regidate)"
					+ "values(board_seq.nextval, ?, ?, ?, sysdate)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getName());
			preparedStatement.setString(2, memberDTO.getId());
			preparedStatement.setString(3, memberDTO.getPw());

			result = preparedStatement.executeUpdate(); // 쿼리문 실행 후 결과를 정수로 보냄

			if (result > 0) {

				System.out.println(memberDTO.getName() + "님 회원가입에 성공하셨습니다.");
				connection.commit(); // 영구 저장

			} else {

				System.out.println(memberDTO.getName() + "님 회원가입에 실패하셨습니다.");
				connection.rollback();

			} // if문 종료

		} catch (SQLIntegrityConstraintViolationException e) {

			System.out.println("동일한 아이디가 있어 회원가입에 실패했습니다. 회원 서비스 메뉴로 이동합니다.");

		} catch (SQLException e) {

			System.out.println("예외 발생: sign() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류

		} finally {

			preparedStatement.close();

		}

	} // sign(회원가입) 메서드 종료

	public MemberDTO login(MemberDTO session) throws SQLException {
		// 회원가입한 아이디와 비밀번호로 로그인한다(아이디 비밀번호 동일시 로그인)

		try {

			String sql = "select * from member where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, session.getId());
			preparedStatement.setString(2, session.getPw());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				session.setMno(resultSet.getInt("mno"));
				session.setName(resultSet.getString("name"));
				session.setId(resultSet.getString("id"));
				session.setPw(resultSet.getString("pw"));
				session.setRegidate(resultSet.getDate("regidate"));

				System.out.println(session.getName() + "님 로그인에 성공하셨습니다.");
				connection.commit(); // 영구 저장

				return session;

			} else {

				System.out.println("로그인에 실패하셨습니다.");
				connection.rollback();
			}

		} catch (SQLException e) {

			System.out.println("예외 발생: login() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류

		} finally {

			preparedStatement.close();
			resultSet.close();

		}

		return session;

	} // login(로그인) 메서드 종료

	public MemberDTO readOne(String pw, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 내 회원정보 확인

		try {

			String sql = "select mno, name, id, pw, regidate from member where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, session.getId());
			preparedStatement.setString(2, pw);
			
			resultSet = preparedStatement.executeQuery(); // 쿼리문 실행 후 결과를 표로 받는다

			if (resultSet.next()) { // 검색 결과가 있으면

				MemberDTO memberDTO = new MemberDTO(); // 빈 객체 생성

				memberDTO.setMno(resultSet.getInt("mno"));
				memberDTO.setName(resultSet.getString("name"));
				memberDTO.setId(resultSet.getString("id"));
				memberDTO.setPw(resultSet.getString("pw"));
				memberDTO.setRegidate(resultSet.getDate("regidate"));
				// 데이터 베이스에 있는 행을 객체로 넣기 완료

				System.out.println("===============================");
				System.out.println("번호: " + memberDTO.getMno());
				System.out.println("이름: " + memberDTO.getName());
				System.out.println("아이디: " + memberDTO.getId());
				System.out.println("비밀번호: " + memberDTO.getPw());
				System.out.println("가입 날짜:" + memberDTO.getRegidate());

			} else { // 검색 결과가 없으면

				System.out.println(session.getName() + "님 회원정보를 볼 수 없습니다.");

			} // if문 종료

		} catch (SQLException e) {

			System.out.println("예외 발생: login() 메서드를 확인하세요.");
			e.printStackTrace(); // 빨간색 오류

		} finally {

			resultSet.close();
			preparedStatement.close();

		}

		return session;

	} // readOne(내 회원정보 확인) 메서드 종료

	public MemberDTO modify(String pw, Scanner input, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 회원정보를 수정한다

		System.out.println("[수정할 수 있는 건 비밀번호뿐입니다.]");

		System.out.print("변경할 비밀번호: ");
		session.setPw(input.next());

		try {

			String sql = "update member set pw=? where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, session.getPw());
			preparedStatement.setString(2, session.getId());
			preparedStatement.setString(3, pw);

			result = preparedStatement.executeUpdate(); // 쿼리문 실행 후 결과를 정수로 보낸다

			if (result > 0) {

				System.out.println(session.getName() + "님 비밀번호가 변경되었습니다.");
				connection.commit(); // 영구 저장

			} else {

				System.out.println(session.getName() + "님 비밀번호 변경에 실패하셨습니다.");
				connection.rollback();

			} // if문 종료

		} catch (SQLException e) {

			System.out.println("예외 발생: modify() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류

		} finally {

			preparedStatement.close();

		}

		return session;

	} // modify(내 회원정보 수정) 메서드 종료

	public MemberDTO deleteOne(String selectid, String selectpw, MemberDTO session) throws SQLException {
		// 현재 로그인한 아이디와 입력한 비밀번호가 동일한 경우 회원 탈퇴를 진행한다

		try {

			String sql = "delete from member where id=? and pw=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, selectid);
			preparedStatement.setString(2, selectpw);

			result = preparedStatement.executeUpdate(); // 쿼리문 실행 후 결과를 정수로 리턴

			if (result > 0) {

				System.out.println(session.getName() + "님 회원 탈퇴되었습니다. 안녕히가세요.");
				connection.commit(); // 영구 저장

			} else {

				System.out.println(session.getName() + "님 회원 탈퇴에 실패하셨습니다.");
				connection.rollback();

			}

			System.out.println("============================");
			selectAll(session); // 삭제 후 전체 리스트 보기(확인용)

		} catch (SQLException e) {

			System.out.println("예외 발생: deleteOne() 메서드와 sql문을 확인하세요.");
			e.printStackTrace(); // 빨간색 오류

		} finally {

			preparedStatement.close();

		}

		return session;

	} // deleteOne(회원 탈퇴) 메서드 종료

	public MemberDTO selectAll(MemberDTO session) throws SQLException {
		// SQL를 사용하여 전체 목록 보기 결과 출력

		try {

			String sql = "select mno, name, id, pw, regidate from member order by regidate desc";
			// 데이터베이스에 member 테이블 내용을 가져오는 쿼리문

			statement = connection.createStatement(); // 쿼리문을 실행 객체 생성
			resultSet = statement.executeQuery(sql); // 쿼리문을 실행하여 결과를 표로 받는다.

			System.out.println("번호\t 이름\t 비밀번호\t 가입날짜\t");

			while (resultSet.next()) { // 결과 표에 위에서부터 아래까지 내려오면서 출력

				System.out.print(resultSet.getInt("mno") + "\t");
				System.out.print(resultSet.getString("name") + "\t");
				System.out.print(resultSet.getString("id") + "\t");
				System.out.print(resultSet.getString("pw") + "\t");
				System.out.println(resultSet.getDate("regidate") + "\t");

			}

			System.out.println("==========모든 회원 목록 보기 메서드 종료=========");

		} catch (SQLException e) { // 오류 발생시 예외 처리문

			System.out.println("selectAll() 메서드에 쿼리문이 잘못되었습니다.");
			e.printStackTrace(); // 빨간색 오류

		} finally { // 항상 실행문

			resultSet.close();
			statement.close();
			// 열린 객체를 닫아야 다른 메서드도 정상 작동함

		}

		return session;

	} // selectAll(모든 회원정보 보기) 메서드 종료

}
