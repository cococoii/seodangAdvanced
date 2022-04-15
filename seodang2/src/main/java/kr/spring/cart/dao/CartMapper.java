package kr.spring.cart.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.cart.vo.CartVO;
import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

public interface CartMapper {
	
	//질문 + 기존 장바구니를 만들 때는 수정관련 부분이 2개있었는데, 하나만 있어도 되는지. 
	
	//+ (user_num과 class_num이 동일한) CartVO 반환
	@Select("SELECT * FROM ocart WHERE class_num =#{class_num} AND user_num =#{user_num} AND class_kind=#{class_kind}")
	public CartVO searchCartVO(CartVO cartVO);
	
	
	//1. 장바구니 등록 
	@Insert("INSERT INTO ocart (cart_num, user_num, class_num, class_kind, order_quantity)"
			+ " VALUES (ocart_seq.nextval, #{user_num},#{class_num},#{class_kind},#{order_quantity})")
	public void insertCart(CartVO cartVO);

	//2-4. 전체 장바구니 목록 불러오기	
	@Select("SELECT * FROM ocart WHERE user_num=#{user_num} ORDER BY reg_date DESC")
	public List<CartVO> getCartList(int user_num);
	
	
	// CartVO에 담을 OnclassVO 데이터 뽑아오기
	@Select("SELECT * FROM onclass WHERE on_num=#{class_num}")
	public OnclassVO getOnclass(int class_num); 
	
	// CartVO에 담을 OffclassVO 데이터 뽑아오기
	@Select("SELECT * FROM offclass WHERE off_num=#{class_num}")
	public OffclassVO getOffclass(int class_num); 
	
	// CartVO에 담을 kitVO 데이터 뽑아오기
	@Select("SELECT * FROM okit WHERE kit_num=#{class_num}")
	public KitVO getKit(int class_num); 
	
	
	//+ sub_total들을 모두 합한 전체 total구하기
	@Select("SELECT SUM(sub_total) FROM ocart WHERE user_num=#{user_num}")
	public int getCartTotal(int user_num);
	
	
	//3. 장바구니  - kit수량 회원번호별 수정	(동일 회원 + 동일 상품 수량변경시 상품의 주문수량만 update) 
	@Update("UPDATE ocart SET order_quantity=#{order_quantity} WHERE cart_num=#{cart_num}")
	public void updateKitOrderQuantity(CartVO cartVO);
	
	//4. 장바구니 상세	: 해당 cart_num과 일치하는 데이터 모두 뽑아오기
	@Select("SELECT * FROM ocart WHERE cart_num =#{cart_num}")
	public CartVO getCartDetail(CartVO cart);
	
	//5. 회원번호(user_num)별 총 구입액
	@Select("SELECT SUM(sub_total) FROM ocart WHERE user_num=#{user_num}")
	public int getTotalByUser_num(int user_num);
	
	//6. 장바구니 삭제
	@Delete("DELETE FROM ocart WHERE cart_num =#{cart_num}")
	public void deleteCart (int cart_num);
}
