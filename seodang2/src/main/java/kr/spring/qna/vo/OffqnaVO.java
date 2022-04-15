package kr.spring.qna.vo;

import java.io.IOException;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class OffqnaVO {		//오프라인 클래스 1:1게시판

	private int offqna_num;
	private int off_num;
	private int user_num;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private Date reg_date;
	private Date modify_date;
	
	private MultipartFile upload;	//파일업로드시 parameter로 넘어오는 부분 
	private byte[] uploadfile;		//파일(byte배열로 처리하기 위한)
	private String filename;		//파일명
	private String id;
	private String name;
	private int auth;	/*0탈퇴,1.정지,2일반,3관리자 // default=2*/
	
	
	//* 업로드 파일 처리하는 setter (파일upload By.blob타입)
	public void setUpload(MultipartFile upload)throws IOException {	//getBytes()사용시 IOException
		this.upload = upload;
		
		setUploadfile(upload.getBytes());
		
		setFilename(upload.getOriginalFilename());
	}
	

	//* toString()재정의
	@Override
	public String toString() {
		return "OffqnaVO [offqna_num=" + offqna_num + ", off_num=" + off_num + ", user_num=" + user_num + ", title="
				+ title + ", content=" + content + ", reg_date=" + reg_date + ", modify_date=" + modify_date
				+ ", upload=" + upload + ", filename=" + filename + ", id=" + id + ", name=" + name + ", auth=" + auth
				+ "]";
	}




	public int getOffqua_num() {
		return offqna_num;
	}
	public void setOffqua_num(int offqua_num) {
		this.offqna_num = offqua_num;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public MultipartFile getUpload() {
		return upload;
	}

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
	
	
	
}
