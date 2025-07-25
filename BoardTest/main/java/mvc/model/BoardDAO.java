package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


import mvc.database.DBConnection;

public class BoardDAO {
	
	private static BoardDAO instance;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
			return instance;
	}
	
	// board 테이블의 레코드 개수
	public int getListCount(String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql;
			if (items == null || items.isEmpty() || text == null || text.isEmpty()) {
				sql = "select count(*) from board";
				pstmt = conn.prepareStatement(sql);
			} else {
				sql = "SELECT count(*) FROM board where " + items + " LIKE ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + text + "%");
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				x = rs.getInt(1);
			
		} catch (Exception ex) {
			System.out.println("getListCount() 에러: " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		
		return x;
		
	}
	
	// board 테이블의 레코드 가져오기
	public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int total_record = getListCount(items, text);
		int start = (page - 1) * limit;
		int index = start + 1;
		
		String sql;
		ArrayList<BoardDTO> list = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			
			if (items == null || items.isEmpty() || text == null || text.isEmpty()) {
				sql = "select * from board ORDER BY num DESC LIMIT ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, limit);
			} else {
				sql = "SELECT * FROM board where " + items + " LIKE ? ORDER BY num DESC LIMIT ?, ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,  "%" + text + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, limit);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				/* board.setId(rs.getString("id")); */
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
				list.add(board);

			}
			
		} catch (Exception ex) {
			System.out.println("getBoardList():" + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		
		return list;
		
	}
	
	// member 테이블에서 인증된 id의 사용자명 가져오기
	/*
	 * public String getLoginNameById(String id) { Connection conn = null;
	 * PreparedStatement pstmt = null; ResultSet rs = null;
	 * 
	 * String name = null; String sql = "select name from member where id = ?";
	 * 
	 * try {
	 * 
	 * conn = DBConnection.getConnection(); pstmt = conn.prepareStatement(sql);
	 * 
	 * pstmt.setString(1, id);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * if (rs.next()) { name = rs.getString("name"); }
	 * 
	 * return name;
	 * 
	 * } catch (Exception ex) {
	 * 
	 * System.out.println("getBoardByNum(): " + ex);
	 * 
	 * } finally {
	 * 
	 * try {
	 * 
	 * if (rs != null) rs.close(); if (pstmt != null) pstmt.close(); if (conn !=
	 * null) conn.close();
	 * 
	 * } catch (Exception ex) {
	 * 
	 * throw new RuntimeException(ex.getMessage());
	 * 
	 * } }
	 * 
	 * return name;
	 * 
	 * }
	 */
	
	// board  테이블에 새로운 글 삽입하기
	public void insertBoard(BoardDTO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			
			String sql = "INSERT INTO board (name, subject, content, regist_day, hit, ip) VALUES (?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			/* pstmt.setInt(1, board.getNum()); */
			/* pstmt.setString(2, board.getId()); */
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getRegist_day());
			pstmt.setInt(5, board.getHit());
			pstmt.setString(6, board.getIp());
			
			System.out.println(">>> insertBoard() 호출됨");
			System.out.println("name = " + board.getName());
			System.out.println("subject = " + board.getSubject());
			System.out.println("content = " + board.getContent());
			System.out.println("regist_day = " + board.getRegist_day());
			System.out.println("ip = " + board.getIp());
			
			pstmt.executeUpdate();
		} catch (Exception ex) {
			
			System.out.println("insertBoard(): 예외 발생");
			ex.printStackTrace(); // 예외 전부 출력
			
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	
	// 선택된 글의 조회수 증가시키기
	public void updateHit(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "select hit from board where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			int hit = 0;
			
			if (rs.next())
				hit = rs.getInt("hit") + 1;
			
			sql = "update board set hit=? where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();

		} catch (Exception ex) {
			System.out.println("updateHit(): " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	
	// 선택된 글 상세 내용 가져오기
	public BoardDTO getBoardByNum(int num, int page) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDTO board = null;
		
		updateHit(num);
		String sql = "select * from board where num = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				/* board.setId(rs.getString("id")); */
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
			}
			
			return board;
			
		} catch (Exception ex) {
			System.out.println("getBoardByNum(): " + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return null;
	}
	
	// 선택된 글 내용 수정하기
	public void updateBoard(BoardDTO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update board set name=?, subject=?, content=? where num=?";
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			conn.setAutoCommit(false);
			
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNum());
			
			pstmt.executeUpdate();
			conn.commit();
			
		} catch (Exception ex) {
			System.out.println("updateBoard(): " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	
	// 선택된 글 삭제하기
	public void deleteBoard(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from board where num=?";
		
		try {
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception ex) {
			System.out.println("deleteBoard(): " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	
	public ArrayList<BoardDTO> searchBoard(String items, String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardDTO> list = new ArrayList<>();
		
		String sql = "";
		
		if ("subject".equals(items)) {
			sql = "SELECT * FROM board WHERE subject LIKE ?";
		} else if ("content".equals(items)) {
			sql = "SELECT * FROM board WHERE content LIKE ?";
		} else if ("name".equals(items)) {
			sql = "SELECT * FROM board WHERE name LIKE ?";
		}
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			String kw ="%" + keyword + "%";
			pstmt.setString(1, kw);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setRegist_day(rs.getString("regist_day"));
				dto.setHit(rs.getInt("hit"));
				dto.setName(rs.getString("name"));
				
				list.add(dto); // 결과 저장
			}
			
			}catch (Exception e) {
				e.printStackTrace();
		}
		
		return list;
		
	}
	
}
