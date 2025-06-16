package Member.DTO;

import java.sql.Date;

public class BoardDTO {
	// 게시판의 객체를 담당하고 게터/세터
	// 필드(테이블에 있는 모든 자료를 입력)
	private int bno;
	private String btitle;
	private String bcontent;
	private String bwriter;
	private Date bdate; // import java.sql.Date;

	// 기본 생성자

	// 메서드 -> 게터(출력) / 세터(입력)
	public int getBno() {
		return bno;
	}

	public String getBtitle() {
		return btitle;
	}

	public String getBcontent() {
		return bcontent;
	}

	public String getBwriter() {
		return bwriter;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}

	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}

	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

}
