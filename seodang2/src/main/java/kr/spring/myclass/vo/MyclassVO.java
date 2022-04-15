package kr.spring.myclass.vo;

public class MyclassVO {
	public int on_num;
	public int off_num;
	public int user_num;
	public String on_name;
	public int on_price;
	private String off_name;
	private String off_limit;
	private String off_price;
	private int onoff;
	private String mimage;
	public int category_num;
	
	
	

	public int getCategory_num() {
		return category_num;
	}
	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}
	public String getMimage() {
		return mimage;
	}
	public void setMimage(String mimage) {
		this.mimage = mimage;
	}
	public int getOnoff() {
		return onoff;
	}
	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}
	public int getOn_num() {
		return on_num;
	}
	public void setOn_num(int on_num) {
		this.on_num = on_num;
	}
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getOn_name() {
		return on_name;
	}
	public void setOn_name(String on_name) {
		this.on_name = on_name;
	}
	public int getOn_price() {
		return on_price;
	}
	public void setOn_price(int on_price) {
		this.on_price = on_price;
	}
	public String getOff_name() {
		return off_name;
	}
	public void setOff_name(String off_name) {
		this.off_name = off_name;
	}
	public String getOff_limit() {
		return off_limit;
	}
	public void setOff_limit(String off_limit) {
		this.off_limit = off_limit;
	}
	public String getOff_price() {
		return off_price;
	}
	public void setOff_price(String off_price) {
		this.off_price = off_price;
	}
	
	
}
