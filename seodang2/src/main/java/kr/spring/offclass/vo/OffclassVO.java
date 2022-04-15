package kr.spring.offclass.vo;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class OffclassVO {
	private int off_num;//클래스 번호
	@Range(min=1,max=5)
	private int category_num;
	@NotEmpty
	private String off_name;//클래스 이름
	@Range(min=1,max=99)
	private int off_limit;//클래스 정원
	@Min(0)
	private int off_price;//클래스 가격
	@NotEmpty
	private String off_content;//클래스 내용
	private MultipartFile off_upload;
	private String off_filename;//파일명
	private byte[] off_uploadfile;
	private int user_num;
	private Date reg_date;
	private Date modify_date;
	@Size(min=5,max=5)
	private String offzipcode;
	@NotEmpty
	private String offaddress1;
	@NotEmpty
	private String offaddress2;
	
	private String name;
	private int like_sum;
	private String photo_name;//선생님 프로필 사진
	private int rating;
	private int rating_percent;
	
	
	//다중업로드 메인이미지
	private String mimage;
	
	public String getMimage() {
		return mimage;
	}

	public void setMimage(String mimage) {
		this.mimage = mimage;
	}
	//다중업로드 메인이미지	

	//업로드 파일 처리
	public void setOff_upload(MultipartFile off_upload) throws IOException{
		this.off_upload = off_upload;
		setOff_uploadfile(off_upload.getBytes());
		setOff_filename(off_upload.getOriginalFilename());
	}
	
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
	public int getCategory_num() {
		return category_num;
	}
	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}
	public String getOff_name() {
		return off_name;
	}
	public void setOff_name(String off_name) {
		this.off_name = off_name;
	}
	public int getOff_limit() {
		return off_limit;
	}

	public void setOff_limit(int off_limit) {
		this.off_limit = off_limit;
	}

	public int getOff_price() {
		return off_price;
	}
	public void setOff_price(int off_price) {
		this.off_price = off_price;
	}

	public String getOff_content() {
		return off_content;
	}
	public void setOff_content(String off_content) {
		this.off_content = off_content;
	}
	public MultipartFile getOff_upload() {
		return off_upload;
	}

	public String getOff_filename() {
		return off_filename;
	}
	public void setOff_filename(String off_filename) {
		this.off_filename = off_filename;
	}
	public byte[] getOff_uploadfile() {
		return off_uploadfile;
	}
	public void setOff_uploadfile(byte[] off_uploadfile) {
		this.off_uploadfile = off_uploadfile;
	}
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public String getOffzipcode() {
		return offzipcode;
	}

	public void setOffzipcode(String offzipcode) {
		this.offzipcode = offzipcode;
	}

	public String getOffaddress1() {
		return offaddress1;
	}

	public void setOffaddress1(String offaddress1) {
		this.offaddress1 = offaddress1;
	}

	public String getOffaddress2() {
		return offaddress2;
	}

	public void setOffaddress2(String offaddress2) {
		this.offaddress2 = offaddress2;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLike_sum() {
		return like_sum;
	}

	public void setLike_sum(int like_sum) {
		this.like_sum = like_sum;
	}
	public String getPhoto_name() {
		return photo_name;
	}

	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getRating_percent() {
		return rating_percent;
	}

	public void setRating_percent(int rating_percent) {
		this.rating_percent = rating_percent;
	}

	@Override
	public String toString() {
		return "OffclassVO [off_num=" + off_num + ", category_num=" + category_num + ", off_name=" + off_name
				+ ", off_limit=" + off_limit + ", off_price=" + off_price + ", off_content=" + off_content
				+ ", off_upload=" + off_upload + ", off_filename=" + off_filename + ", user_num=" + user_num
				+ ", reg_date=" + reg_date + ", modify_date=" + modify_date + ", offzipcode=" + offzipcode
				+ ", offaddress1=" + offaddress1 + ", offaddress2=" + offaddress2 + ", name=" + name + ", like_sum="
				+ like_sum + ", photo_name=" + photo_name + ", rating=" + rating + ", rating_percent="
				+ rating_percent + "]";
	}
	
}
