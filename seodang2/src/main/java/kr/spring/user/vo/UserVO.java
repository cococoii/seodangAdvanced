package kr.spring.user.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

public class UserVO {
	private int user_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}")
	private String id;
	private int auth;
	@NotEmpty
	private String name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}")
	private String passwd;
	@NotEmpty
	private String phone;
	private MultipartFile upload;
	private byte[] photo;
	private String photo_name;
	@Size(min=5,max=5)
	private String zipcode;
	@NotEmpty
	private String address1;
	@NotEmpty
	private String address2;
	@Range(min=1,max=100)
	private int age;
	@Email
	@NotEmpty
	private String email;
	private Date reg_date;
	private Date modify_date;
	
	@Pattern(regexp="^[A-Za-z0-9]{4,12}")
	private String now_passwd;
	
	//정지회원,일반회원 비밀번호 체크
	public boolean isCheckedPassword(String userPasswd) {
		if(auth>=1 && passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
	
	//이미지 blob 처리
	public void setUpload(MultipartFile upload) throws IOException{
		this.upload = upload;
		//MultiFile -> byte[]
		setPhoto(upload.getBytes());
		//파일이름
		setPhoto_name(upload.getOriginalFilename());
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MultipartFile getUpload() {
		return upload;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhoto_name() {
		return photo_name;
	}

	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getNow_passwd() {
		return now_passwd;
	}

	public void setNow_passwd(String now_passwd) {
		this.now_passwd = now_passwd;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	@Override
	public String toString() {
		return "UserVO [user_num=" + user_num + ", id=" + id + ", auth=" + auth + ", name=" + name + ", passwd="
				+ passwd + ", phone=" + phone + ", upload=" + upload + ", photo_name=" + photo_name + ", zipcode="
				+ zipcode + ", address1=" + address1 + ", address2=" + address2 + ", age=" + age + ", email=" + email
				+ ", reg_date=" + reg_date + ", now_passwd=" + now_passwd + "]";
	}
	
}
