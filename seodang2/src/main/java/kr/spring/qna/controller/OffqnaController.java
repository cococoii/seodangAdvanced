package kr.spring.qna.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.qna.service.OffqnaService;
import kr.spring.qna.vo.OffqnaVO;

@Controller
public class OffqnaController {
	
	private static final Logger logger = LoggerFactory.getLogger(OffqnaController.class);
	
	//의존관계 주입
	@Autowired
	private OffqnaService offqnaService;
	
	//자바빈 초기화
	public OffqnaVO initCommand() {
		return new OffqnaVO();
	}
	
	//글 등록 폼
	@GetMapping("/offqna/offqnaWrite.do") //view 생성
	public String form() {
		return "offqnaWrite";//tiles 등록
	}
	

	//글 등록 폼에서 전송된 데이터 처리
	@PostMapping("/offqna/offqnaWrite.do")
	public String submit(@Valid OffqnaVO offqnaVO, BindingResult result,
			HttpSession session, HttpServletRequest request) {

		//<1>로그처리
		logger.info("<<Offqna게시판 글 저장>> : " + offqnaVO);
		
		//<2>유효성 체크결과 오류가 있으면 form호출
		if(result.hasErrors()) {
			return form();
		}
		
		//<3>유효성 체크결과 오류가 없을시
		//회원번호 세팅
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		offqnaVO.setUser_num(user_num);
		//글쓰기
		offqnaService.insertOffqna(offqnaVO);
		
		//<4> 글등록 후 목록으로 redirect
		return "redirect:/offqna/offqnaList.do";
	}	
	
	//offqna 글 목록
	@RequestMapping("/offqna/offqnaList.do")
	public ModelAndView process() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("offqnaList");
		
		return mav;
	}
	
}
