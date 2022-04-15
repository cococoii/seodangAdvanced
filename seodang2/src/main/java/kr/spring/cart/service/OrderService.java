package kr.spring.cart.service;

import java.util.List;


import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.OrderDetailVO;
import kr.spring.cart.vo.OrderVO;

public interface OrderService {

	//1. CartVO반환
	public CartVO searchCartVO(int cart_num);
	
	//2-1. order과 order_detail에서 사용할 공통 sequence 생성
	public int orderSequence();
	
	//2-2. 주문목록  등록(oorder)
	public void insertOrder(OrderVO orderVO);
	
	//2-3. 주문목록 등록(oorder_detail)
	public void insertOrderDetail(OrderDetailVO detailVO);
	
	//+ 전체 주문목록 구하기(for 관리자)
	public List<OrderVO> getAdminOrderList();
	
	//2-4. 주문번호(order_num)에 해당하는 oorder_detail의 상품정보 구하기 - (주문시 재고수 차감)
	public List<OrderDetailVO> getDetailList(int order_num);
	
	//2-4(2). 주문번호(order_num)에 해당하는 oorder_detail의 상품정보 구하기 - (주문시 재고수 차감)
	public List<OrderDetailVO> getDetailListDetailnum(int order_num);
	
	//2-5. 해당 user_num의 orderVO 리스트 추출
	public List<OrderVO> getOrderList(int user_num);
	
	// + 해당 주문번호(order_num)의 orderVO 추출
	public OrderVO getOrderVO(int order_num);
	
	// + 해당 주문번호(order_num)의 orderVO정보 수정 
	public void updateOrderVO(OrderVO orderVO);
	
	//+ 해당 odetail_num으로 oorderDetailVO의 정보 불러오기
	public OrderDetailVO getOrderDetail(int detail_num);
	
	//+ 해당 odetail_num으로 oorderDetailVO의 status(배송)정보 수정
	public void updateStatus(OrderDetailVO detailVO);
	
	//2-6.  oorder_detail의 Kit 주문수량을 재고에서 차감 - (주문시 재고수 차감)
	public void updateKitQuantity(CartVO transVO);
		
	//2-7.  oorder_detail의 오프클래스 주문수를 오프클래스 정원에서 차감 - (주문시 재고수 차감)
	public void updateOffQuantity(int class_num);
	
}
