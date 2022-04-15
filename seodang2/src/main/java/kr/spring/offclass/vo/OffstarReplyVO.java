package kr.spring.offclass.vo;

import kr.spring.util.DurationFromNow;

public class OffstarReplyVO {
	private int offre_num;
	private int offstar_num;
	private int user_num;
	private String offre_content;
	private String offre_date;
	private String photo_name;
	private String name;
	
	public int getOffre_num() {
		return offre_num;
	}
	public void setOffre_num(int offre_num) {
		this.offre_num = offre_num;
	}
	public int getOffstar_num() {
		return offstar_num;
	}
	public void setOffstar_num(int offstar_num) {
		this.offstar_num = offstar_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getOffre_content() {
		return offre_content;
	}
	public void setOffre_content(String offre_content) {
		this.offre_content = offre_content;
	}
	public String getOffre_date() {
		return offre_date;
	}
	public void setOffre_date(String offre_date) {
		this.offre_date = DurationFromNow.getTimeDiffLabel(offre_date);
	}
	
	
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "OffstarReplyVO [offre_num=" + offre_num + ", offstar_num=" + offstar_num + ", user_num=" + user_num
				+ ", offre_content=" + offre_content + ", offre_date=" + offre_date + "]";
	}
	
}
