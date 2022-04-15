package kr.spring.onclass.vo;

import java.sql.Date;

public class OstarVO {
	private int ostar_num;
	private int user_num;
	private int on_num;
	private String rating;
	private String text;
	private Date reg_date;
	
	//아이디 조인
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getOstar_num() {
		return ostar_num;
	}
	public void setOstar_num(int ostar_num) {
		this.ostar_num = ostar_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getOn_num() {
		return on_num;
	}
	public void setOn_num(int on_num) {
		this.on_num = on_num;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "OstarVO [ostar_num=" + ostar_num + ", user_num=" + user_num + ", on_num=" + on_num + ", rating="
				+ rating + ", text=" + text + ", reg_date=" + reg_date + ", id=" + id + "]";
	}
	
	
}
