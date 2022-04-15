package kr.spring.onclass.vo;

import java.sql.Date;

import kr.spring.util.DurationFromNow;

public class OstarReplyVO {
	private int ore_num;
	private int ostar_num; //부모 글번호
	private int user_num;
	private String ore_content;
	private String ore_date;
	private String id;
	
	public int getOre_num() {
		return ore_num;
	}
	public void setOre_num(int ore_num) {
		this.ore_num = ore_num;
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
	public String getOre_content() {
		return ore_content;
	}
	public void setOre_content(String ore_content) {
		this.ore_content = ore_content;
	}
	public String getOre_date() {
		return ore_date;
	}
	public void setOre_date(Date ore_date) {
		this.ore_date = DurationFromNow.getTimeDiffLabel(ore_date);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "OstarReplyVO [ore_num=" + ore_num + ", ostar_num=" + ostar_num + ", user_num=" + user_num
				+ ", ore_content=" + ore_content + ", ore_date=" + ore_date + ", id=" + id + "]";
	}
	
	
	
}
