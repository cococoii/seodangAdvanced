package kr.spring.qna.vo;

import kr.spring.util.DurationFromNow;

public class OffqnaReplyVO {	//오프라인 클래스 1:1게시판 답변

	private int offqnare_num;
	private int offqua_num;
	private int user_num;
	private String re_content; 
	private String re_date; 	
	private String re_mdate;
		
	
	//날짜처리 변경
	public void setRe_date(String re_date) {
		this.re_date = DurationFromNow.getTimeDiffLabel(re_date);
	}
	public void setRe_mdate(String re_mdate) {
		this.re_mdate = DurationFromNow.getTimeDiffLabel(re_mdate);
	}
	
	
	//toString재정의
	@Override
	public String toString() {
		return "OffqnaReplyVO [offqnare_num=" + offqnare_num + ", offqua_num=" + offqua_num + ", user_num=" + user_num
				+ ", re_content=" + re_content + ", re_date=" + re_date + ", re_mdate=" + re_mdate + "]";
	}
	
	public int getOffqnare_num() {
		return offqnare_num;
	}
	public void setOffqnare_num(int offqnare_num) {
		this.offqnare_num = offqnare_num;
	}
	public int getOffqua_num() {
		return offqua_num;
	}
	public void setOffqua_num(int offqua_num) {
		this.offqua_num = offqua_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getRe_content() {
		return re_content;
	}
	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}
	public String getRe_date() {
		return re_date;
	}

	public String getRe_mdate() {
		return re_mdate;
	}
	
}
