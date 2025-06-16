package Member.DTO;

import java.sql.Date;

public class MemberDTO {
	// 게시판의 객체를 담당하고 게터/세터
	// 필드(테이블에 있는 모든 자료를 입력)
	private int mno;
	private String name;
	private String id;
	private String pw;
	private Date regidate; // import java.sql.Date;

	// 기본 생성자

	// 메서드 -> 게터(출력) / 세터(입력)
	public int getMno() {
		return mno;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public Date getRegidate() {
		return regidate;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}

}
