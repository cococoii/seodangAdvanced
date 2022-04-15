package kr.spring.myclass.vo;

import java.sql.Date;

public class PaymentVO {
	public int onreg_num;
	public int user_num;
	public int on_num;
	public int on_payment; //결제 방법 계좌이체(1), 카드결제(2)
	public int on_status; //수강신청상태(1), 수강취소상태(2)
	public Date on_regdate;
	public Date on_moregdate;
//////////////////////////////////	
	public int off_num;
	public int getOff_num() {
		return off_num;
	}
	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}
/////////////////////////////////
	/////ouser에서 가져온 조인 값/////
	public String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/////ouser에서 가져온 조인 값/////	
	
	//수강중인 인원 체크
	private int peopleCount;
		
	public int getPeopleCount() {
		return peopleCount;
	}
	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}
	//수강중인 인원 체크
	
	public int getOnreg_num() {
		return onreg_num;
	}
	public void setOnreg_num(int onreg_num) {
		this.onreg_num = onreg_num;
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
	public int getOn_payment() {
		return on_payment;
	}
	public void setOn_payment(int on_payment) {
		this.on_payment = on_payment;
	}
	public int getOn_status() {
		return on_status;
	}
	public void setOn_status(int on_status) {
		this.on_status = on_status;
	}
	public Date getOn_regdate() {
		return on_regdate;
	}
	public void setOn_regdate(Date on_regdate) {
		this.on_regdate = on_regdate;
	}
	public Date getOn_moregdate() {
		return on_moregdate;
	}
	public void setOn_moregdate(Date on_moregdate) {
		this.on_moregdate = on_moregdate;
	}
	@Override
	public String toString() {
		return "PaymentVO [onreg_num=" + onreg_num + ", user_num=" + user_num + ", on_num=" + on_num + ", on_payment="
				+ on_payment + ", on_status=" + on_status + ", on_regdate=" + on_regdate + ", on_moregdate="
				+ on_moregdate + "]";
	}
	
}
