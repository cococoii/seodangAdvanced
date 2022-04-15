package kr.spring.cart.vo;

import java.sql.Date;

import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

public class CartVO {
	private int cart_num;
	private int user_num;
	private int class_num;
	private String class_kind;
	
	private int order_quantity;	/*주문 수량 / default:1*/
	
	private int sub_total;	/*sub_total에서 연산해줘야되기때문에 VO들이 있지만 프로퍼티로 quantity를 넣어줬음*/
	private int total;
	private Date date;
	
	private OnclassVO onclassVO;
	private OffclassVO offclassVO;
	private KitVO kitVO;
	

	//toString
	@Override
	public String toString() {
		return "CartVO [cart_num=" + cart_num + ", user_num=" + user_num + ", class_num=" + class_num + ", class_kind="
				+ class_kind + ", order_quantity=" + order_quantity + ", sub_total=" + sub_total + ", total=" + total
				+ ", date=" + date + ", onclassVO=" + onclassVO + ", offclassVO=" + offclassVO + ", kitVO=" + kitVO
				+ "]";
	}

	
	//sub_total 연산 
	public int getSub_total() {
		return sub_total;
	}



	public int getTotal() {
		return total;
	}



	public void setTotal(int total) {
		this.total = total;
	}



	public int getCart_num() {
		return cart_num;
	}

	public void setCart_num(int cart_num) {
		this.cart_num = cart_num;
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public int getClass_num() {
		return class_num;
	}

	public void setClass_num(int class_num) {
		this.class_num = class_num;
	}

	public String getClass_kind() {
		return class_kind;
	}

	public void setClass_kind(String class_kind) {
		this.class_kind = class_kind;
	}

	public int getOrder_quantity() {
		return order_quantity;
	}

	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OnclassVO getOnclassVO() {
		return onclassVO;
	}

	public void setOnclassVO(OnclassVO onclassVO) {
		this.onclassVO = onclassVO;
	}

	public OffclassVO getOffclassVO() {
		return offclassVO;
	}

	public void setOffclassVO(OffclassVO offclassVO) {
		this.offclassVO = offclassVO;
	}

	public KitVO getKitVO() {
		return kitVO;
	}

	public void setKitVO(KitVO kitVO) {
		this.kitVO = kitVO;
	}

	public void setSub_total(int sub_total) {
		this.sub_total = sub_total;
	}
}
