package kr.spring.cart.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.spring.cart.service.CartService;
import kr.spring.cart.vo.CartVO;
import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.service.OffclassService;
import kr.spring.offclass.vo.OffTimetableVO;
import kr.spring.offclass.vo.OffclassVO;

@Controller
public class CartController {

	//로그처리 준비
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	
	//(1) 의존관계 주입
	@Autowired
	private CartService cartService;
	@Autowired
	private OffclassService offService;
	
	
	//(2) 장바구니 등록 
	@RequestMapping("/cart/cartInsert.do")
	public String insert(CartVO cartVO,HttpSession session, HttpServletRequest request) {

		// [ 로그인이 되어있지 않은경우 ] 
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {
			return "redirect:/user/login.do";
		}
		// [ 로그인이 되어있는 경우 ]
		cartVO.setUser_num(user_num);	//user_num 넣어주기  
		
		int class_num = cartVO.getClass_num();

		//(user_num과 class_num이 동일한) CartVO 반환 : 현재 장바구니에 동일한 상품(class_num)이 있는지 확인
		CartVO cartItem = cartService.searchCartVO(cartVO);
		
		if(cartItem==null) {	//cartItem에 동일한 class_num상품이 없는 경우 => insert
			cartService.insertCart(cartVO);
			
			if(cartVO.getClass_kind().equals("on")) {	//CartVO에 OnclassVO담기 & subtotal담기
				cartVO.setOnclassVO(cartService.getOnclass(class_num));
				cartVO.setSub_total(cartVO.getOnclassVO().getOn_price()*cartVO.getOrder_quantity());
			}else if(cartVO.getClass_kind().equals("off")) {	//CartVO에 OffclassVO담기 & subtotal담기
				cartVO.setOffclassVO(cartService.getOffclass(class_num));
				cartVO.setSub_total(cartVO.getOffclassVO().getOff_price()*cartVO.getOrder_quantity());
			}else if(cartVO.getClass_kind().equals("kit")) {
				cartVO.setKitVO(cartService.getKit(class_num));
				cartVO.setSub_total(cartVO.getKitVO().getKit_price()*cartVO.getOrder_quantity());
			}
			
		}else {
			/*----- onclass와 offclass의 INSERT -----*/
			//(1) class_num이 동일한 상품(on_class, off_class)이 이미 장바구니에 존재한다면 => 불가 메시지 alert
			if(cartItem.getClass_kind().equals("on") || cartItem.getClass_kind().equals("off")) {
				request.setAttribute("message", "온라인 및 오프라인 클래스는 1인 1건만 주문가능합니다.");
				request.setAttribute("url", request.getContextPath()+"/cart/cartList.do");
				return "/common/resultView";
			}
			
			/*-----kit의 INSERT-----*/	
			if(cartItem.getClass_kind().equals("kit")) {
				//(1) 동일한 상품(kit)이 이미 장바구니에 존재한다면 => 재고수량 확인  후 수량수정 (주문수량보다 재고수량이 초과시 alert)
				int order_quantity = cartVO.getOrder_quantity();		//현재 장바구니에 추가하려는 수량(default=1)
				int cart_quantity = cartItem.getOrder_quantity();		//장바구니에 담겨있는 kit의 수량 
				int kit_quantity = cartService.getKit(cartVO.getClass_num()).getKit_quantity(); 	//해당 kit상품의 재고수량
				
				//	최종 수량 			= 원래 장바구니에 담겨있는 수량  + 새로 추가하려는 수량
				int result_quantity = cart_quantity + order_quantity;
				
				// 재고수량이 부족한경우 (주문수량이 재고수량보다 많은경우)
				if(kit_quantity < result_quantity) {
					request.setAttribute("message", "재고수량이 부족합니다.");
					request.setAttribute("url", request.getContextPath()+"/cart/cartList.do");
					return "/common/resultView";
				}else {
					//동일한 상품이 장바구니에 존재하고, 주문수량이 재고수량보다 적을경우 update
					cartVO.setOrder_quantity(result_quantity);
					cartVO.setCart_num(cartItem.getCart_num());
					cartService.updateKitOrderQuantity(cartVO);	
				}			
			}	
		}
		
		return "redirect:/cart/cartList.do";
	}
	

	//(2) 장바구니 목록	 //(+나중에 장바구니 등록 날짜 조회기능 +)
	@RequestMapping("/cart/cartList.do")
	public ModelAndView process(HttpSession session, HttpServletRequest request) {
		
		//<1> 로그인 확인
		ModelAndView mav = new ModelAndView();
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
			if(user_num == null) {
				mav.setViewName("redirect:/user/login.do");
				return mav;
			}
		
		List<CartVO> list = null;
		list = cartService.getCartList(user_num);	//user_num에 해당하는 전체 장바구니 데이터를 뽑아옴
		
		List<OffTimetableVO> timeList = null;
		
		
		
		// Q. VO를 직접넣어주지않으면 자동으로 VO가 들어가지 않음.. 왜  ? INSERT시킬 떄는 VO그대로 다 넣었는데..
		
		
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getClass_kind().equals("on")) {	//list에 OnclassVO담기   &  (list에 sub_total담기)
				list.get(i).setOnclassVO(cartService.getOnclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOnclassVO().getOn_price()*list.get(i).getOrder_quantity());
			}
			else if(list.get(i).getClass_kind().equals("off")) {	//list에 OffclassVO담기   &  (list에 sub_total담기)
				logger.info("진입");
				list.get(i).setOffclassVO(cartService.getOffclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOffclassVO().getOff_price()*list.get(i).getOrder_quantity());
				timeList = offService.selectListOffTimeDate(list.get(i).getOffclassVO().getOff_num());
				logger.info("<<timeList>>: "+timeList);
				Date time = timeList.get(0).getTime_date();
				 
				mav.addObject("time", time);
			}
			else if(list.get(i).getClass_kind().equals("kit")) {	//list에 kitVO담기   &  list에 sub_total담기
				list.get(i).setKitVO(cartService.getKit(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getKitVO().getKit_price()*list.get(i).getOrder_quantity());
			}
		}
		
		//<3> request에 데이터 저장
		mav.setViewName("cartList");
		mav.addObject("user_num", user_num);	
		mav.addObject("list", list);
		return mav;
	}
	

	//(3) 장바구니 kit 수량 수정
	@RequestMapping("/cart/modifyCart.do")
	@ResponseBody
	public Map<String,String> updateKit(int cart_num, int order_quantity, 
										int kit_quantity, HttpSession session){
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Map<String,String> map = new HashMap<String,String>();
		
		//로그인이 되어있지 않은경우
		if(user_num==null) {
			map.put("result","logout");
		}
		
		//로그인이 된 경우 - 상품의 수량이 부족한 경우 (재고수<주문수량)
		else if(kit_quantity<order_quantity){
			map.put("result","noQuantity");
			
		//로그인이 된 경우 - 상품의 갯수 변경 가능한 경우	
		}else{
			CartVO cartVO = new CartVO();
			cartVO.setCart_num(cart_num);
			cartVO.setOrder_quantity(order_quantity);
			cartService.updateKitOrderQuantity(cartVO);
			map.put("result","success");
		}
		return map;
	}
			
	
	//(4) 장바구니 상품 삭제	 
		@RequestMapping("/cart/deleteCart.do")
		@ResponseBody
		public Map<String,String> delete(int cart_num, HttpSession session){
			Integer user_num = (Integer)session.getAttribute("session_user_num");
			Map<String,String> map = new HashMap<String,String>();
			
			//로그인이 되어있지 않은경우
			if(user_num==null) {
				map.put("result","logout");
			}
			//로그인이 된 경우
			else {
				cartService.deleteCart(cart_num);
				map.put("result", "success");
			}
			return map;
		}
}
