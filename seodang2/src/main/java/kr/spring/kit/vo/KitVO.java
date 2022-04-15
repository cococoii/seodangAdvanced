package kr.spring.kit.vo;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class KitVO {
	
	private int kit_num; //상품번호
	@NotEmpty
	private String kit_name; //상품명
	//@Range(min=1,max=5)
	//private int category_num;//카테고리
	@NotNull
	private int kit_price; //가격
	@NotNull
	private int kit_quantity; //판매 수량
	@NotEmpty
	private String kit_content; //상품 정보
	private Date reg_date; //등록일
	private Date modify_date; //수정일
	private int user_num;//회원번호
	private int hit;//조회수
	private MultipartFile upload;	//파일업로드시 parameter로 넘어오는 부분 
	private byte[] uploadfile;		//파일(byte배열로 처리하기 위한)
	private String filename;		//파일명
	private String kit_content2;
	
	
	private int like_count;
	
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	//대표이미지 다중이미지
	private String mimage;
	public String getMimage() {
		return mimage;
	}
	public void setMimage(String mimage) {
		this.mimage = mimage;
	}
	//대표이미지 다중이미지

	//업로드 파일
	public MultipartFile getUpload() {
		return upload;
	}
	public void setUpload(MultipartFile upload) throws IOException{
		this.upload = upload;
		//MultipartFile -> byte[] 변환
		setUploadfile(upload.getBytes());
		//파일명 구하기
		setFilename(upload.getOriginalFilename());
	}
	
	/*public int getCategory_num() {
		return category_num;
	}
	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}*/
	public byte[] getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(byte[] uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getKit_num() {
		return kit_num;
	}
	public void setKit_num(int kit_num) {
		this.kit_num = kit_num;
	}
	public String getKit_name() {
		return kit_name;
	}
	public void setKit_name(String kit_name) {
		this.kit_name = kit_name;
	}
	public int getKit_price() {
		return kit_price;
	}
	public void setKit_price(int kit_price) {
		this.kit_price = kit_price;
	}
	public int getKit_quantity() {
		return kit_quantity;
	}
	public void setKit_quantity(int kit_quantity) {
		this.kit_quantity = kit_quantity;
	}
	public String getKit_content() {
		return kit_content;
	}
	public void setKit_content(String kit_content) {
		this.kit_content = kit_content;
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
	public int getUser_num() {
		return user_num;
	}
	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}
	public String getKit_content2() {
		return kit_content2;
	}
	public void setKit_content2(String kit_content2) {
		this.kit_content2 = kit_content2;
	}
	@Override
	public String toString() {
		return "KitVO [kit_num=" + kit_num + ", kit_name=" + kit_name + ", kit_price=" + kit_price + ", kit_quantity="
				+ kit_quantity + ", kit_content=" + kit_content + ", reg_date=" + reg_date + ", modify_date="
				+ modify_date + ", user_num=" + user_num + ", hit=" + hit + ", upload=" + upload + ", filename="
				+ filename + ", kit_content2=" + kit_content2 + "]";
	}
	

	
	
	
	
}
