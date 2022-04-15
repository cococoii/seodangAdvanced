package kr.spring.onclass.vo;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

public class OnstarVO {
	private int onstar_num;
	private int user_num;
	private int on_num;
	@Range(min=1,max=5)
	private int rating;
	@NotEmpty
	private String text;
	private String reg_date;
	//이름
	private String name;
	private String on_name;
	private String photo_name;//사용자 사진
	private int rating_percent;
	
	//클래스 등록한 사람의 정보
	private String writer_photo;
	private String writer_num;
	private String writer_name;
	private String onre_content;
	private String onre_date;
	private int onre_num;
	public int getOnstar_num() {
		return onstar_num;
	}
	public void setOnstar_num(int onstar_num) {
		this.onstar_num = onstar_num;
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOn_name() {
		return on_name;
	}
	public void setOn_name(String on_name) {
		this.on_name = on_name;
	}
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}
	public int getRating_percent() {
		return rating_percent;
	}
	public void setRating_percent(int rating_percent) {
		this.rating_percent = rating_percent;
	}
	public String getWriter_photo() {
		return writer_photo;
	}
	public void setWriter_photo(String writer_photo) {
		this.writer_photo = writer_photo;
	}
	public String getWriter_num() {
		return writer_num;
	}
	public void setWriter_num(String writer_num) {
		this.writer_num = writer_num;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getOnre_content() {
		return onre_content;
	}
	public void setOnre_content(String onre_content) {
		this.onre_content = onre_content;
	}
	public String getOnre_date() {
		return onre_date;
	}
	public void setOnre_date(String onre_date) {
		this.onre_date = onre_date;
	}
	public int getOnre_num() {
		return onre_num;
	}
	public void setOnre_num(int onre_num) {
		this.onre_num = onre_num;
	}
	
	
}
