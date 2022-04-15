package kr.spring.kit.vo;

public class UploadFileKitVO {
	private int file_num;
	private int kit_num;
	private String o_name;
	private String file_name;
	private int file_size;
	public int getFile_num() {
		return file_num;
	}
	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}
	public int getKit_num() {
		return kit_num;
	}
	public void setKit_num(int kit_num) {
		this.kit_num = kit_num;
	}
	public String getO_name() {
		return o_name;
	}
	public void setO_name(String o_name) {
		this.o_name = o_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	} 
}
