package kr.spring.cart.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.cart.dao.CartMapper;
import kr.spring.cart.vo.CartVO;
import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

@Service
public class CartServiceImpl implements CartService{
	
	//* 의존관계 주입
	@Autowired
	private CartMapper cartMapper;
	
	
	//+ CartVO 반환
	@Override
	public CartVO searchCartVO(CartVO cartVO) {
		return cartMapper.searchCartVO(cartVO);
	}
	

	//1. 장바구니 등록 
	@Override
	public void insertCart(CartVO cartVO) {
		cartMapper.insertCart(cartVO);
	}


	//2-4. 전체 장바구니 목록 불러오기
	@Override
	public List<CartVO> getCartList(int user_num) {
		return cartMapper.getCartList(user_num);
	}

	
	// ** CartVO에 담을 OnclassVO 데이터 뽑아오기
	@Override
	public OnclassVO getOnclass(int class_num) {
		return cartMapper.getOnclass(class_num);
	}
	
	// ** CartVO에 담을 OffclassVO 데이터 뽑아오기
	public OffclassVO getOffclass(int class_num) {
		return cartMapper.getOffclass(class_num);
	}
	
	// ** CartVO에 담을 kitVO 데이터 뽑아오기
	@Override
	public KitVO getKit(int class_num) {
		return cartMapper.getKit(class_num);
	}
	
	//3. 장바구니  - kit수량 회원번호별 수정
	@Override
	public void updateKitOrderQuantity(CartVO cartVO) {
		cartMapper.updateKitOrderQuantity(cartVO);
	}
	
	//4. 장바구니 상세	: 해당 cart_num과 일치하는 데이터 모두 뽑아오기
	@Override
	public CartVO getCartDetail(CartVO cart) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//5. 회원번호(user_num)별 총 구입액
	@Override
	public int getTotalByUser_num(int user_num) {
		// TODO Auto-generated method stub
		return 0;
	}	
	
	//6. 장바구니 삭제
	@Override
	public void deleteCart(int cart_num) {
		cartMapper.deleteCart(cart_num);
	}
}
