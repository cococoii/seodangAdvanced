package kr.spring.cart.controller;

import java.sql.Date; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.cart.dao.OrderMapper;
import kr.spring.cart.service.CartService;
import kr.spring.cart.service.OrderService;
import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.OrderDetailVO;
import kr.spring.cart.vo.OrderVO;
import kr.spring.offclass.service.OffclassService;
import kr.spring.offclass.vo.OffTimetableVO;
import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;

@Controller
public class OrderController {

	//로그처리 준비
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	//(1) 의존관계 주입
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	@Autowired
	private OffclassService offService;



	//(2) 주문 화면
	@RequestMapping("/cart/orderForm.do")
	public ModelAndView orderForm(HttpSession session) {

		ModelAndView mav = new ModelAndView();

		// 로그인이 되어있지 않은경우
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {
			mav.setViewName("redirect:/user/login.do");
			return mav;
		}

		// 로그인이 되어있는 경우 
		List<OffTimetableVO> timeList = null;

		List<CartVO>list = cartService.getCartList(user_num);	//user_num에 해당하는 전체 장바구니 데이터를 뽑아옴

		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getClass_kind().equals("on")) {	//list에 OnclassVO담기   &  (list에 sub_total담기)
				list.get(i).setOnclassVO(cartService.getOnclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOnclassVO().getOn_price()*list.get(i).getOrder_quantity());
			}
			else if(list.get(i).getClass_kind().equals("off")) {	//list에 OffclassVO담기   &  (list에 sub_total담기)
				list.get(i).setOffclassVO(cartService.getOffclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOffclassVO().getOff_price()*list.get(i).getOrder_quantity());
				timeList = offService.selectListOffTimeDate(list.get(i).getOffclassVO().getOff_num());
				Date time = timeList.get(timeList.size()-1).getTime_date();//get()부분 수정필요

				mav.addObject("time", time);
			}
			else if(list.get(i).getClass_kind().equals("kit")) {	//list에 kitVO담기   &  list에 sub_total담기
				list.get(i).setKitVO(cartService.getKit(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getKitVO().getKit_price()*list.get(i).getOrder_quantity());
			}
		}

		//<3> request에 데이터 저장
		mav.setViewName("orderForm");
		mav.addObject("user_num", user_num);	
		mav.addObject("list", list);
		return mav;
	}


	//(3-1) OrderForm - OrderVO 초기화
	@ModelAttribute
	public OrderVO initCommand(HttpSession session) {
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		UserVO userVO = userService.selectUser(user_num);	//회원가입된 user의 정보 ( value로 값을 뿌리지 않아도됨 )

		OrderVO orderVO = new OrderVO();
		orderVO.setReceive_name(userVO.getName());
		orderVO.setReceive_post(userVO.getZipcode());
		orderVO.setReceive_address1(userVO.getAddress1());
		orderVO.setReceive_address2(userVO.getAddress2());
		orderVO.setReceive_phone(userVO.getPhone());

		return orderVO;
	}

	//(3-2) 주문등록 (VO 유효성체크 ver)
	@GetMapping("/cart/order.do")
	public String form(HttpSession session, Model model) {

		Integer user_num = (Integer)session.getAttribute("session_user_num");
		UserVO userVO = userService.selectUser(user_num);	//회원가입된 user의 정보 (나중에 placeholder로 뿌리기)
		model.addAttribute("userVO", userVO);

		return "orderForm";
	}	

	//(3-3) 주문등록 처리
	@PostMapping("/cart/order.do")
	public ModelAndView submit(@Valid OrderVO orderVO, BindingResult result, HttpSession session, Model model, HttpServletRequest request) {

		logger.info("<<1. 들어오는 orderVO확인 >>: "+orderVO);

		//로그인체크
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {
			return new ModelAndView("redirect:/user/login.do");
		}

		// item_name만들어주기
		List<CartVO>list = cartService.getCartList(user_num);
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getClass_kind().equals("on")) {	//list에 OnclassVO담기   &  (list에 sub_total담기)
				list.get(i).setOnclassVO(cartService.getOnclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOnclassVO().getOn_price()*list.get(i).getOrder_quantity());
			}
			else if(list.get(i).getClass_kind().equals("off")) {	//list에 OffclassVO담기   &  (list에 sub_total담기)
				list.get(i).setOffclassVO(cartService.getOffclass(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getOffclassVO().getOff_price()*list.get(i).getOrder_quantity());
			}
			else if(list.get(i).getClass_kind().equals("kit")) {	//list에 kitVO담기   &  list에 sub_total담기
				list.get(i).setKitVO(cartService.getKit(list.get(i).getClass_num()));
				list.get(i).setSub_total(list.get(i).getKitVO().getKit_price()*list.get(i).getOrder_quantity());
			}
		}

		String item_name = "";
		if(list.size()==1) {
			if(list.get(0).getClass_kind().equals("on")) {
				item_name = list.get(0).getOnclassVO().getOn_name();
			}else if(list.get(0).getClass_kind().equals("off")) {
				item_name = list.get(0).getOffclassVO().getOff_name();
			}else if(list.get(0).getClass_kind().equals("kit")) {
				item_name = list.get(0).getKitVO().getKit_name();
			}
		}else {
			if(list.get(0).getClass_kind().equals("on")) {
				item_name = list.get(0).getOnclassVO().getOn_name()+" 외 "+ (list.size()-1)+"건";
			}else if(list.get(0).getClass_kind().equals("off")) {
				item_name = list.get(0).getOffclassVO().getOff_name()+" 외 "+ (list.size()-1)+"건";
			}else if(list.get(0).getClass_kind().equals("kit")) {
				item_name = list.get(0).getKitVO().getKit_name()+" 외 "+ (list.size()-1)+"건";
			}
		}

		int order_num = orderService.orderSequence();

		orderVO.setOrder_num(order_num);
		orderVO.setItem_name(item_name);
		orderVO.setUser_num(user_num);

		logger.info("<<주문확인>>"+orderVO);

		if(result.hasErrors()) {
			return orderForm(session);	//*******model인자 고쳐야한다.********
		}

		//oorder테이블에 orderVO(장바구니주문)데이터 insert
		orderService.insertOrder(orderVO);
		logger.info("<<oorder테이블에 Insert완료 >>");

		//oorder_detail테이블에 orderVO(장바구니주문)데이터 목록으로 insert
		OrderDetailVO detailVO = new OrderDetailVO();

		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getClass_kind().equals("on")) {
				detailVO.setItem_name(list.get(i).getOnclassVO().getOn_name());
				detailVO.setItem_price(list.get(i).getOnclassVO().getOn_price());
				detailVO.setStatus(5);//배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)
			}else if(list.get(i).getClass_kind().equals("off")) {
				detailVO.setItem_name(list.get(i).getOffclassVO().getOff_name());
				detailVO.setItem_price(list.get(i).getOffclassVO().getOff_price());
				detailVO.setStatus(5);//배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)
			}else if(list.get(i).getClass_kind().equals("kit")) {
				detailVO.setItem_name(list.get(i).getKitVO().getKit_name());
				detailVO.setItem_price(list.get(i).getKitVO().getKit_price());
				detailVO.setStatus(1);	//배송 및 수강 상태 (1:배송대기 /2:배송준비중 /3:배송중 /4:배송완료 /5:수강신청/6:수강중/7:수강완료/8:주문취소)
			}
			detailVO.setClass_kind(list.get(i).getClass_kind());
			detailVO.setClass_num(list.get(i).getClass_num());
			detailVO.setItem_total(list.get(i).getSub_total());
			detailVO.setOrder_quantity(list.get(i).getOrder_quantity());
			detailVO.setOrder_num(orderVO.getOrder_num());	
			orderService.insertOrderDetail(detailVO);
		}
		logger.info("<<oorder_detail테이블에 Insert완료 >>");


		// 주문시 재고수 차감 

		//1. 해당 order_num에 해당하는 order_detail의 정보 모두 읽어오기
		List<OrderDetailVO>detailList = orderService.getDetailList(order_num);

		//2. 읽어온 order_detail의 데이터 list들을 for문을 돌면서 order_quantity(주문수량)를 구한 후
		//   해당 주문수량만큼, 해당 class_num의 재고수량을 차감
		for(int i=0; i<detailList.size(); i++) {
			int order_quantity = detailList.get(i).getOrder_quantity();	//주문수량
			int class_num = detailList.get(i).getClass_num();

			CartVO transVO = new CartVO();
			transVO.setOrder_quantity(order_quantity);
			transVO.setClass_num(class_num);

			if(detailList.get(i).getClass_kind().equals("kit")) {
				// kit주문시 재고수 차감
				orderService.updateKitQuantity(transVO);
				logger.info("<< kit재고수 차감 완료 >>");
			}else if(detailList.get(i).getClass_kind().equals("off")) {
				// 오프라인클래스 주문시 정원(off_limit) 차감
				orderService.updateOffQuantity(class_num);
				logger.info("<< 오프클래스 정원 차감 완료 >>");
			}
		}

		//해당 user_num에 해당하는 order테이블의 orderVO리스트 모두 읽어오기
		List<OrderVO>orderList = orderService.getOrderList(user_num);
		model.addAttribute("orderList", orderList); 
		logger.info("<< orderList확인 >>" + orderList);

		model.addAttribute("detailList", detailList);
		logger.info("<< detailList확인 >>" + detailList);
		
		model.addAttribute("message", "주문이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath()+"/cart/orderList.do");
		
		return new ModelAndView("common/resultView");
	}


	@RequestMapping("/cart/orderListByUser.do")
	public String listByUser(@RequestParam int user_num, HttpSession session, Model model) {

		//로그인체크
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		if(session_user_num == null) {
			return "redirect:/user/login.do";
		}

		//해당 user_num에 해당하는 order테이블의 orderVO리스트 모두 읽어오기
		List<OrderVO>orderList = orderService.getOrderList(user_num);
		model.addAttribute("orderList", orderList); 
		logger.info("<< orderList확인 >>" + orderList);

		return "orderList";
	}

	//(4) 주문목록 
	@RequestMapping("/cart/orderList.do")
	public String list(HttpSession session, Model model) {

		//로그인체크
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {
			return "redirect:/user/login.do";
		}

		//해당 user_num에 해당하는 order테이블의 orderVO리스트 모두 읽어오기
		List<OrderVO>orderList = orderService.getOrderList(user_num);
		model.addAttribute("orderList", orderList); 
		logger.info("<< orderList확인 >>" + orderList);

		return "orderList";
	}

	//(5) 주문내역 상세
	@RequestMapping("/cart/orderDetail.do")
	public String orderList(HttpSession session, @RequestParam int order_num, Model model) {

		//로그인체크
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		model.addAttribute("user_auth", user_auth);
		
		if(user_num == null) {
			return "redirect:/user/login.do";
		}
		logger.info("<< order_num 확인 >>" + order_num);

 
		// order_num에 해당하는 order_detail데이터 전부 출력
		List<OrderDetailVO> detailList = orderService.getDetailList(order_num);
		for(int i=0; i<detailList.size(); i++) {
			detailList.get(i).setDetail_num(orderService.getDetailListDetailnum(order_num).get(i).getDetail_num());
		}
		
		System.out.println("~~~~~~~~~~~~" + detailList);

		List<OffTimetableVO> timeList = null;

		for(int i=0; i<detailList.size(); i++) {
			if(detailList.get(i).getClass_kind().equals("off")) {	//list에 OffclassVO담기   &  (list에 sub_total담기)
				timeList = offService.selectTime(detailList.get(i).getClass_num());
				model.addAttribute("time", timeList);
			}
			model.addAttribute("detailList", detailList);
		}

		OrderVO orderVO = orderService.getOrderVO(order_num);
		model.addAttribute("orderVO", orderVO);
		
		//OrderDetailVO detailVO = new OrderDetailVO();
		//detailVO = orderService.getOrderDetail(detail_num);
		//model.addAttribute("detailVO", detailVO);
		
		return "orderDetail";
	}

	//(6) 주문내역 - 구매 및 배송정보 수정 
	@RequestMapping("/cart/modifyOrder.do")
	public String modifyDelivery(HttpSession session, OrderVO orderVO, Model model, HttpServletRequest request ) {

		//로그인체크
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		model.addAttribute("user_auth", user_auth);
		
		if(user_num == null) {
			return "redirect:/user/login.do";
		}

		// order_num에 해당하는 order_detail데이터 전부 출력
		List<OrderDetailVO> detailList = orderService.getDetailList(orderVO.getOrder_num());
		for(int i=0; i<detailList.size(); i++) {
			detailList.get(i).setDetail_num(orderService.getDetailListDetailnum(orderVO.getOrder_num()).get(i).getDetail_num());
		}
		
		List<OffTimetableVO> timeList = null;

		for(int i=0; i<detailList.size(); i++) {
			if(detailList.get(i).getClass_kind().equals("off")) {	//list에 OffclassVO담기   &  (list에 sub_total담기)
				timeList = offService.selectTime(detailList.get(i).getClass_num());
				model.addAttribute("time", timeList);
			}
			model.addAttribute("detailList", detailList);
		}

		logger.info("orderVO구매정보 : " + orderVO);

		
		logger.info("작성자와 로그인한 회원 일치여부 확인 : 작성자 - 로그인 : " + orderVO.getUser_num() + "//" + user_num);
		
		if(user_num==orderVO.getUser_num() || user_auth>2) {//강사와 관리자는 변경가능
			logger.info("<<수정 전, form에서 들어온 orderVO >>" + orderVO);
			orderService.updateOrderVO(orderVO);
			logger.info("<<수정 완료 orderVO>>" + orderVO );

			model.addAttribute("message", "구매 및 배송정보가 수정되었습니다.");
			model.addAttribute("url",request.getContextPath()+"/cart/orderDetail.do?order_num="+orderVO.getOrder_num());
			return "common/resultView";
		}

		model.addAttribute("message", "해당상품 구매회원이 아닙니다.");
		model.addAttribute("url", request.getContextPath()+"/user/login.do");
		
		
		return "common/resultView";
	}

	
	//관리자의 배송정보 수정 1. OrderDetailVO 초기화
	@ModelAttribute
	public OrderDetailVO initOrderDetailVO() {

		OrderDetailVO detailVO = new OrderDetailVO();
		
		return detailVO;
	}
	
	
	//관리자의 배송정보 수정 2.
	@RequestMapping("/cart/ModifyDelivery.do")
	public ModelAndView ModiftDelivery(HttpSession session, OrderDetailVO detailVO, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();

		logger.info("수정전 받아온  DetailVO확인 !!!  : " + detailVO);
		
		// 로그인이 되어있지 않은경우, 관리자로 로그인하지 않은 경우 (추후 + 해당강사로 로그인하지 않을 경우)
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_num == null || user_auth <3) {
			mav.setViewName("redirect:/user/login.do");
			return mav;
		}

		orderService.updateStatus(detailVO);
		logger.info("수정후 DetailVO확인 !!!  : " + detailVO);
		
		mav.setViewName("redirect:/cart/orderDetail.do?order_num="+detailVO.getOrder_num());
		return mav;
	}	

	//관리자의 주문관리
	@RequestMapping("/cart/adminOrderList.do")
	public ModelAndView adminOrderList(HttpSession session) {

		ModelAndView mav = new ModelAndView();

		// 로그인이 되어있지 않은경우
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null) {
			mav.setViewName("redirect:/user/login.do");
			return mav;
		}

		List<OrderVO> orderList = orderService.getAdminOrderList();
		mav.addObject("list", orderList);
		mav.setViewName("adminOrderList");
		return mav;
	}


	//관리자의 주문관리 상세 및 수정
	@RequestMapping("/cart/orderDetailByAdmin.do")
	public ModelAndView adminOrderListByAdmin(HttpSession session,OrderVO orderVO, HttpServletRequest request) {

		logger.info("받아온orderVO 확인  : " + orderVO);

		ModelAndView mav = new ModelAndView();

		// 로그인이 되어있지 않은경우, 관리자로 로그인하지 않은 경우
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_num == null || user_auth <4) {
			mav.setViewName("redirect:/user/login.do");
			return mav;
		}

		List<OrderDetailVO> detailList = orderService.getDetailList(orderVO.getOrder_num());

		mav.addObject("detailList", detailList);
		mav.setViewName("orderDetailByAdmin");
		return mav;
	}

	

	
	
	
	
}
