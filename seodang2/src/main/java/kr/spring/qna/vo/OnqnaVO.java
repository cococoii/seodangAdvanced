package kr.spring.qna.vo;

import java.io.IOException;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class OnqnaVO {	//온라인 클래스 1:1게시판
	private int onqna_num;
	private int on_num;
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
		return "OnqnaVO [onqna_num=" + onqna_num + ", on_num=" + on_num + ", user_num=" + user_num + ", title=" + title
				+ ", content=" + content + ", reg_date=" + reg_date + ", modify_date=" + modify_date + ", upload="
				+ upload + ", filename=" + filename + ", id=" + id + ", name=" + name + ", auth=" + auth + "]";
	}

	
	
	
	public int getOnqna_num() {
		return onqna_num;
	}

	public void setOnqna_num(int onqna_num) {
		this.onqna_num = onqna_num;
	}
	public int getOn_num() {
		return on_num;
	}
	public void setOn_num(int on_num) {
		this.on_num = on_num;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}
	
	
	
}
