package kr.spring.myclass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.myclass.service.MyclassService;
import kr.spring.myclass.vo.PaymentVO;
import kr.spring.util.PagingUtil;

@Controller
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	public MyclassService myclassService;
	
	//구매한 클래스 내 정보 구매목록에 저장
	@PostMapping("/onclass/payment.do")
	public String payment(PaymentVO paymentVO,HttpSession session,Model model,HttpServletRequest request) {
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		paymentVO.setUser_num(user_num);
		logger.info("!!paymentVO print : " + paymentVO);
		/* System.out.println("강의 넘버 : " + paymentVO.on_num); */
		int on_num = paymentVO.getOn_num();
		paymentVO.setOnreg_num(myclassService.selectSeq());
		
		if(myclassService.overlap(on_num,user_num) == 0) {
			System.out.println("중간체크");
			myclassService.insertRegister(paymentVO);
			model.addAttribute("message", "구매 완료 !!");
			model.addAttribute("url", request.getContextPath() + "/main/main.do");
		}else if(myclassService.overlap(on_num,user_num) >= 1) {
			model.addAttribute("message", "이미 신청한 강의 입니다");
			model.addAttribute("url", request.getContextPath() + "/onclass/onclassDetail.do?on_num="+on_num);
		}
		
		return "common/resultView";
	}
	
	//구매한 클래스 내 정보 구매목록에 저장
		@PostMapping("/offclass/paymentoff.do")
		public String paymentoff(PaymentVO paymentVO,HttpSession session,Model model,HttpServletRequest request) {
			Integer user_num = (Integer)session.getAttribute("session_user_num");
			paymentVO.setUser_num(user_num);
			logger.info("!!paymentVO off : " + paymentVO);
			
			int off_num = paymentVO.off_num;
			paymentVO.setOnreg_num(myclassService.selectSeq());
			
			if(myclassService.overlapoff(off_num,user_num) == 0) {
				myclassService.insertRegisterOff(paymentVO);
				model.addAttribute("message", "구매 완료 !!");
				model.addAttribute("url", request.getContextPath() + "/main/main.do");
			}else if(myclassService.overlapoff(off_num,user_num) >= 1) {
				model.addAttribute("message", "이미 신청한 강의 입니다");
				model.addAttribute("url", request.getContextPath() + "/offclass/offclassDetail.do?off_num="+off_num);
			}
		
			return "common/resultView";
		}
	
	
		//키트 구매한 클래스 내 정보 구매목록에 저장
		@PostMapping("/kit/paymentkit.do")
		public String paymentKit(PaymentVO paymentVO,HttpSession session,Model model,HttpServletRequest request) {
			Integer user_num = (Integer)session.getAttribute("session_user_num");
			paymentVO.setUser_num(user_num);
			logger.info("!!paymentVO : " + paymentVO);
			/* System.out.println("강의 넘버 : " + paymentVO.on_num); */
			
			model.addAttribute("message", "구매 완료 !!");
			model.addAttribute("url", request.getContextPath() + "/main/main.do");
		
			
			return "common/resultView";
		}
	
	
	@RequestMapping("/myclass/myRegisterList.do")
	public ModelAndView classList(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage,
			@RequestParam(value="keyfield",defaultValue="")
			String keyfield,
			@RequestParam(value="keyword",defaultValue="")
			String keyword, HttpSession session
			) {
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("user_num", user_num);
		
		int count = myclassService.selectRowCount2(map);
		
		PagingUtil page = new PagingUtil(keyfield,keyword,
                currentPage,count,4,10,"myRegisterList.do");

		map.put("start",page.getStartCount());
		map.put("end", page.getEndCount());
		
		
		List<PaymentVO> list = null;
			if(count > 0) {
				list = myclassService.selectRegisterList(map);
			}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myRegisterList");
		mav.addObject("user_num",user_num);
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml", page.getPagingHtml());

		return mav;
	}
	
	
	//온라인강의 구매 취소
	@GetMapping("/myclass/deletePayment.do")
	public String delete(int onreg_num,Model model,HttpServletRequest request,HttpSession session,
										PaymentVO pVO) {
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");		
		/* System.out.println(onreg_num); */
		pVO.setOnreg_num(onreg_num);
		pVO.setUser_num(user_num);		
		pVO = myclassService.selectPayment(pVO);
		
		System.out.println("강의 상태 : " + pVO.getOn_status());
		
		int status = pVO.getOn_status(); // 강의상태
		
		if(status == 1) {
			myclassService.deletePayment(onreg_num, user_num);
			model.addAttribute("message", "수강 취소 되었습니다");
			model.addAttribute("url", request.getContextPath() + "/myclass/myRegisterList.do");
		}else if(status == 2) {
			model.addAttribute("message", "이미 취소");
			model.addAttribute("url", request.getContextPath() + "/myclass/myRegisterList.do");
		}
		
		return "common/resultView";
	}
}



































