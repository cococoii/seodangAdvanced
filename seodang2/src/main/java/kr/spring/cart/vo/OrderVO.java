package kr.spring.cart.vo;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class OrderVO {
	
	private int order_num;		//주문번호
	private String item_name; 	//주문상품명
	private int order_total;	//총 구매금액
	@Range(min=1,max=2)
	private int payment;		//지불방식(1:통장입금 /2:카드결제)
	
	private int status;			//배송상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:주문취소)
	
	@NotEmpty
	private String receive_name;
	@Size(min=5,max=5)
	@NotEmpty
	private String receive_post;
	@NotEmpty
	private String receive_address1;
	@NotEmpty
	private String receive_address2;
	@NotEmpty
	private String receive_phone;
	
	private String notice;
	private Date reg_date;
	private Date modify_date;
	private int user_num;
	private String id;
	
	@Override
	public String toString() {
		return "OrderVO [order_num=" + order_num + ", item_name=" + item_name + ", order_total=" + order_total
				+ ", payment=" + payment + ", status=" + status + ", receive_name=" + receive_name + ", receive_post="
				+ receive_post + ", receive_address1=" + receive_address1 + ", receive_address2=" + receive_address2
				+ ", receive_phone=" + receive_phone + ", notice=" + notice + ", reg_date=" + reg_date
				+ ", modify_date=" + modify_date + ", user_num=" + user_num + ", id=" + id + "]";
	}
	
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getOrder_total() {
		return order_total;
	}
	public void setOrder_total(int order_total) {
		this.order_total = order_total;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getReceive_post() {
		return receive_post;
	}
	public void setReceive_post(String receive_post) {
		this.receive_post = receive_post;
	}
	public String getReceive_address1() {
		return receive_address1;
	}
	public void setReceive_address1(String receive_address1) {
		this.receive_address1 = receive_address1;
	}
	public String getReceive_address2() {
		return receive_address2;
	}
	public void setReceive_address2(String receive_address2) {
		this.receive_address2 = receive_address2;
	}
	public String getReceive_phone() {
		return receive_phone;
	}
	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
