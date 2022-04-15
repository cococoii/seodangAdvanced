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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.qna.dao.OqnaMapper;
import kr.spring.qna.service.OqnaService;
import kr.spring.qna.vo.OqnaVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

@Controller
public class OqnaController {

	private static final Logger logger = LoggerFactory.getLogger(OqnaController.class);
	
	//(1) 의존관계 주입
	@Autowired
	private OqnaService oqnaService;
	
	//(2) 자바빈 초기화
	@ModelAttribute
	public OqnaVO initCommand() {
		return new OqnaVO();
	}
	
	//(3) 글등록 form
	@GetMapping("/oqna/oqnaWrite.do")
	public String form() {
		return "oqnaWrite";
	}
	
	//(4) 글등록form에서 전송된 데이터 처리
	@PostMapping("/oqna/oqnaWrite.do")
	public String submit(@Valid OqnaVO oqnaVO, BindingResult result,
						HttpSession session, HttpServletRequest request) {
		
		//<1>로그처리
		logger.info("<<Oqna게시판 글 저장>> : " + oqnaVO);
		
		//<2>유효성 체크결과 오류가 있으면 form호출
		if(result.hasErrors()) {
			return form();
		}
		
		//<3>유효성 체크결과 오류가 없을시
		//회원번호 세팅
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		oqnaVO.setUser_num(user_num);
		//글쓰기
		oqnaService.insertOqna(oqnaVO);
		
		//<4> 글등록 후 목록으로 redirect
		return "redirect:/oqna/oqnaList.do";
	}
	
	

	//(5) 게시판 글 목록
	@RequestMapping("/oqna/oqnaList.do")
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
		int count = oqnaService.selectOqnaRowCount(map);
				
		//<3> 페이지처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,15,10,"oqnaList.do");
		map.put("start", page.getStartCount());	
		map.put("end", page.getEndCount());
		
		//<4> map을 넘겨줘서 글목록 뽑아오기
		List<OqnaVO> list = null;
		if(count >0) {
			list = oqnaService.getOqnaList(map);
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
	
	
	//(6) 게시판 글상세
	@RequestMapping("/oqna/oqnaDetail.do")
	public ModelAndView process(@RequestParam int qna_num, HttpSession session) {
		
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		Integer session_user_auth = (Integer)session.getAttribute("session_user_auth");
		
		//타이틀 HTML 불허
		OqnaVO oqna = oqnaService.selectOqna(qna_num);
		oqna.setTitle(StringUtil.useBrNoHtml(oqna.getTitle()));
		
		//로그처리
		logger.info("<<게시판 글상세 - 로그인한 회원번호 >> : " + session_user_num);
		logger.info("<<게시판 글상세 - 로그인한 회원등급 >> : " + session_user_auth);
		logger.info("<<게시판 글상세 - 글 작성자 oqnaVO >> : " + oqna);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("oqnaDetailView");
		mav.addObject("session_user_num",session_user_num);
		mav.addObject("session_user_auth",session_user_auth);
		mav.addObject("oqna",oqna);

		
		return mav;
		
		//return new ModelAndView("oqnaDetailView","oqna",oqna);
									//타일스 설정	  속성명	속성값
	}
	
	//(6-2) 이미지 출력
	@RequestMapping("/oqna/imageView.do")
	public ModelAndView viewImage(@RequestParam int qna_num) {
		
		OqnaVO oqna = oqnaService.selectOqna(qna_num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile", oqna.getUploadfile());
		mav.addObject("filename",oqna.getFilename());
	
		return mav;
	}
	
	//(6-3) 파일 다운로드
	@RequestMapping("/oqna/file.do")
	public ModelAndView download(@RequestParam int qna_num) {
		OqnaVO oqna = oqnaService.selectOqna(qna_num);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("downloadView");
		mav.addObject("downloadFile", oqna.getUploadfile());
		mav.addObject("filename", oqna.getFilename());
		
		return mav;
	}

	
	
	//(7) 수정form
	@GetMapping("/oqna/oqnaUpdate.do")
	public String formUpdate(@RequestParam int qna_num, Model model) {
		OqnaVO oqnaVO = oqnaService.selectOqna(qna_num);
		model.addAttribute("oqnaVO",oqnaVO);
		
		return "oqnaModify";
	}
	
	//(8) 수정form에서 전송된 데이터 처리
	@PostMapping("/oqna/oqnaUpdate.do")
	public String submitUpdate(@Valid OqnaVO oqnaVO, BindingResult result,
								HttpServletRequest request, Model model) {
		
		//로그 출력
		logger.info("<<글 정보 수정>> : " + oqnaVO);
		
		//<1> 유효성 체크결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			OqnaVO vo = oqnaService.selectOqna(oqnaVO.getQna_num());
			oqnaVO.setFilename(vo.getFilename());
			
			return "oqnaModify";
		}
		
		//<2> 글 수정
		oqnaService.updateOqna(oqnaVO);

		//<3> view에 표시할 메시지를 지정
		model.addAttribute("message", "글 수정 완료");
		model.addAttribute("url", request.getContextPath()+"/oqna/oqnaList.do");
		
		//<4> 스크립트 호출
		return "common/resultView";
	}
	
	
	//(9) 게시판 글 삭제
	@RequestMapping("/oqna/oqnaDelete.do")
	public String submitDelete(@RequestParam int qna_num) {
		oqnaService.deleteOqna(qna_num);
		return "redirect:/oqna/oqnaList.do";
	}
	
}
