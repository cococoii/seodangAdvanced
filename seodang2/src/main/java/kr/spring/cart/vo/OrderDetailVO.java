package kr.spring.cart.vo;

public class OrderDetailVO {
	private int detail_num;
	private int class_num;
	private String class_kind;
	private String item_name;
	private int item_price;
	private int item_total;
	private int order_quantity;
	private int order_num;
	private int status;//배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)


	@Override
	public String toString() {
		return "OrderDetailVO [detail_num=" + detail_num + ", class_num=" + class_num + ", class_kind=" + class_kind
				+ ", item_name=" + item_name + ", item_price=" + item_price + ", item_total=" + item_total
				+ ", order_quantity=" + order_quantity + ", order_num=" + order_num + ", status=" + status + "]";
	}


	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getClass_kind() {
		return class_kind;
	}
	public void setClass_kind(String class_kind) {
		this.class_kind = class_kind;
	}
	public int getClass_num() {
		return class_num;
	}
	public void setClass_num(int class_num) {
		this.class_num = class_num;
	}
	public int getDetail_num() {
		return detail_num;
	}
	public void setDetail_num(int detail_num) {
		this.detail_num = detail_num;
	}

	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	public int getItem_total() {
		return item_total;
	}
	public void setItem_total(int item_total) {
		this.item_total = item_total;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public int getOrder_num() {
		return order_num;
	}
	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}
	
	
	
}
