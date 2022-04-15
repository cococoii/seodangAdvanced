package kr.spring.qna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.qna.service.OnqnaService;
import kr.spring.qna.vo.OnqnaVO;
import kr.spring.util.PagingUtil;



@Controller
public class OnqnaController {

	private static final Logger logger = LoggerFactory.getLogger(OnqnaController.class);
	
	
	//(1)의존관계 주입
	@Autowired
	private OnqnaService onqnaService;
	
	
	//(2)자바빈 초기화
	@ModelAttribute
	public OnqnaVO initCommand() {
		return new OnqnaVO();
	}
	
	//(3)글등록 form
	@GetMapping("/onqna/onqnaWrite.do")	//view생성
	public String form() { 
		return "onqnaWrite";	//tiles등록
	}
	
	//글 등록 폼에서 전송된 데이터 처리
	@PostMapping("/onqna/onqnaWrite.do")
	public String submit(@Valid OnqnaVO onqnaVO, BindingResult result,
			HttpSession session, HttpServletRequest request) {

		//<1>로그처리
		logger.info("<<Oqna게시판 글 저장>> : " + onqnaVO);
		
		//<2>유효성 체크결과 오류가 있으면 form호출
		if(result.hasErrors()) {
			return form();
		}
		
		//<3>유효성 체크결과 오류가 없을시
		//회원번호 세팅
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		onqnaVO.setUser_num(user_num);
		//글쓰기
		onqnaService.insertOnqna(onqnaVO);
		
		//<4> 글등록 후 목록으로 redirect
		return "redirect:/onqna/onqnaList.do";
	}	
	
	//onqna 글 목록
	@RequestMapping("/onqna/onqnaList.do")
	public ModelAndView process(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
								@RequestParam(value="keyfield", defaultValue="") String keyfield,
								@RequestParam(value="keyword", defaultValue="") String keyword,
								HttpSession session){
		
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		//로그처리
		logger.info("<<게시판 글상세 - 회원번호 >> : " + session_user_num);
		
		
		//<1> keyfield, keyword데이터를 Map객체에 넘기기
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		
		//<2> 글의 총 갯수 또는 검색된 글의 갯수
		int count = onqnaService.selectOnqnaRowCount(map);
				
		//<3> 페이지처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,15,10,"onqnaList.do");
		map.put("start", page.getStartCount());	
		map.put("end", page.getEndCount());
		
		//<4> map을 넘겨줘서 글목록 뽑아오기
		List<OnqnaVO> list = null;
		if(count >0) {
			list = onqnaService.getOnqnaList(map);
		}
		
		//<5> request에 데이터 저장
		ModelAndView mav = new ModelAndView();
		mav.setViewName("oqnaList");
		mav.addObject("session_user_num",session_user_num);
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml",page.getPagingHtml());
		
		return mav;
	
	}
	
	
}
