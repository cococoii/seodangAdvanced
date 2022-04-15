package kr.spring.cart.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import kr.spring.cart.vo.CartVO;
import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

public interface CartService {

	//+ CartVO 반환
	public CartVO searchCartVO(CartVO cartVO);
	
	//1. 장바구니 등록 
	public void insertCart(CartVO cartVO);

	
	//2-4. 전체 장바구니 목록 불러오기
	public List<CartVO> getCartList(int user_num);

	// ** CartVO에 담을 OnclassVO 데이터 뽑아오기
	public OnclassVO getOnclass(int class_num); 
	
	// ** CartVO에 담을 OffclassVO 데이터 뽑아오기
	public OffclassVO getOffclass(int class_num); 
	
	// ** CartVO에 담을 kitVO 데이터 뽑아오기
	public KitVO getKit(int class_num); 
	
	//+ sub_total들을 모두 합한 전체 total구하기
	//public int getCartTotal(int user_num);
	
	
	//3. 장바구니  - kit수량 회원번호별 수정	(동일 회원 + 동일 상품 수량변경시 상품의 주문수량만 update) 
	public void updateKitOrderQuantity(CartVO cartVO);
	
	//4. 장바구니 상세	: 해당 cart_num과 일치하는 데이터 모두 뽑아오기
	public CartVO getCartDetail(CartVO cart);
	
	//5. 회원번호(user_num)별 총 구입액	//
	public int getTotalByUser_num(int user_num);
	
	//6. 장바구니 삭제
	public void deleteCart (int cart_num);

	
}
