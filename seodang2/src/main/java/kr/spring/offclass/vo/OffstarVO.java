package kr.spring.offclass.vo;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import kr.spring.util.DurationFromNow;

public class OffstarVO {
	private int offstar_num;
	private int user_num;
	private int off_num;
	@Range(min=1,max=5)
	private int rating;
	@NotEmpty
	private String text;
	private String reg_date;
	//이름
	private String name;
	private String off_name;
	private String photo_name;//사용자 사진
	private int rating_percent;
	
	//클래스 등록한 사람의 정보
	private String writer_photo;
	private String writer_num;
	private String writer_name;
	private String offre_content;
	private String offre_date;
	private int offre_num;
	
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
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
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
		this.reg_date = DurationFromNow.getTimeDiffLabel(reg_date);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOff_name() {
		return off_name;
	}
	public void setOff_name(String off_name) {
		this.off_name = off_name;
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
	
	public int getOffre_num() {
		return offre_num;
	}
	public void setOffre_num(int offre_num) {
		this.offre_num = offre_num;
	}
	@Override
	public String toString() {
		return "OffstarVO [offstar_num=" + offstar_num + ", user_num=" + user_num + ", off_num=" + off_num + ", rating="
				+ rating + ", text=" + text + ", reg_date=" + reg_date + ", name=" + name + ", off_name=" + off_name
				+ ", photo_name=" + photo_name + ", rating_percent=" + rating_percent + ", writer_photo=" + writer_photo
				+ ", writer_num=" + writer_num + ", writer_name=" + writer_name + ", offre_content=" + offre_content
				+ ", offre_date=" + offre_date + ", offre_num=" + offre_num + "]";
	}
	
}
