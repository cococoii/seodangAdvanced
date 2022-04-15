package kr.spring.qna.vo;

import kr.spring.util.DurationFromNow;

public class OnqnaReplyVO {	//온라인 클래스 1:1게시판 답변

	private int onqnare_num;
	private int onqua_num;
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
		return "OnqnaReplyVO [onqnare_num=" + onqnare_num + ", onqua_num=" + onqua_num + ", user_num=" + user_num
				+ ", re_content=" + re_content + ", re_date=" + re_date + ", re_mdate=" + re_mdate + "]";
	}
	
	
	public int getOnqnare_num() {
		return onqnare_num;
	}
	public void setOnqnare_num(int onqnare_num) {
		this.onqnare_num = onqnare_num;
	}
	public int getOnqua_num() {
		return onqua_num;
	}
	public void setOnqua_num(int onqua_num) {
		this.onqua_num = onqua_num;
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

