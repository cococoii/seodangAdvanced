package kr.spring.offclass.vo;

public class OfflikeVO {
	private int offlike_num;
	private int user_num;
	private int off_num;
	private int olike;
	
	public int getOfflike_num() {
		return offlike_num;
	}
	public void setOfflike_num(int offlike_num) {
		this.offlike_num = offlike_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
	public int getOlike() {
		return olike;
	}
	public void setOlike(int olike) {
		this.olike = olike;
	}
	
	@Override
	public String toString() {
		return "OfflikeVO [offlike_num=" + offlike_num + ", user_num=" + user_num + ", off_num=" + off_num + ", olike="
				+ olike + "]";
	}
}
