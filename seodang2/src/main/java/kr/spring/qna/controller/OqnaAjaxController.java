package kr.spring.qna.controller;

import java.security.cert.CollectionCertStoreParameters; 
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.qna.service.OqnaService;
import kr.spring.qna.vo.OqnaReplyVO;
import kr.spring.util.PagingUtil;

@Controller
public class OqnaAjaxController {

	//로그 처리 준비
	private static final Logger logger = LoggerFactory.getLogger(OqnaAjaxController.class);
	
	//rowCount 지정
	private int rowCount = 10;
	
	//1. 의존관계 주입
	@Autowired
	private OqnaService oqnaService;
	
	//2. 부모글 업로드된 파일 삭제 ( 수정form에서 파일삭제 )
	@RequestMapping("/oqna/deleteFile.do")
	@ResponseBody
	public Map<String,String> processFile(int qna_num,HttpSession session){
		
		Map<String,String>map = new HashMap<String,String>();
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num == null){	
			map.put("result", "logout");
		}else {
			oqnaService.deleteOqnaFile(qna_num);
			map.put("result", "success");
		}
		return map;
	}
	
	//3. 댓글등록
	
	@RequestMapping("/oqna/oqnaWriteReply.do")
	@ResponseBody
	public Map <String,String> oqnaWriteReply(OqnaReplyVO oqnaReplyVO, HttpSession session,
											HttpServletRequest request){
		//로그처리
		logger.info("<<댓글등록>> : " + oqnaReplyVO );
		
		//1. 반환시킬 Map객체 생성
		Map<String,String> map = new HashMap<String,String>();
		
		//2. session으로부터 user_num, user_auth 받아오기
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		
		//3. 로그인이 되지 않은 경우
		if(user_num == null) {
			map.put("result", "logout");

		//4. 관리자가 아닌경우
		}else if(user_auth <3){
			map.put("result","notAdmin");
			
		//5. 관리자로 로그인이 된 경우	
		}else {
			//(1) oqnaReplyVO에 회원번호 등록
			oqnaReplyVO.setUser_num(user_num);
			//(2) oqnaReplyVO - 댓글등록 메서드 실행
			oqnaService.insertReply(oqnaReplyVO);
			map.put("result", "success");
		}
		return map;
	}
	
	
	//4. 댓글 목록
	@RequestMapping("/oqna/oqnaListReply.do")
	@ResponseBody
	public Map<String,Object> getReplyList(@RequestParam(value="pageNum", defaultValue="1")int currentPage,
											@RequestParam int qna_num,
											HttpSession session){
		//로그처리
		logger.info("<<댓글 목록 호출>> currentPage : "+ currentPage);
		logger.info("<<댓글 목록 호출 >> 글번호 : " + qna_num);
		
		//<1>Map객체 생성
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("qna_num",qna_num);
		
		//<2>페이지 처리
		int count = oqnaService.selectRowCountReply(map);	//총 댓글의 갯수
		PagingUtil page = new PagingUtil(currentPage, count, rowCount, 10, null);
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		//<3> 댓글 list 만들기
		List<OqnaReplyVO> list = null;
		if(count>0) {
			list = oqnaService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		//<4> 데이터를 json문자열로 만들어서 반환
		Map<String,Object> mapJson = new HashMap<String,Object>();
		mapJson.put("count", count);
		mapJson.put("rowCount", rowCount);
		mapJson.put("list", list);
		mapJson.put("user_num", (Integer)session.getAttribute("session_user_num"));
		
		//<5> Map객체 반환
		return mapJson;
		
	}
	
	
	//5. 댓글 수정
	@RequestMapping("/oqna/oqnaUpdateReply.do")
	@ResponseBody
	public Map<String,String> modifyReply(OqnaReplyVO oqnaReplyVO,
											HttpSession session,
											HttpServletRequest request){
		//로그처리
		logger.info("<<댓글 수정>> : " + oqnaReplyVO);
		
		//<1> Map객체 생성
		Map<String,String>map = new HashMap<String,String>();
		
		//<2> Session으로 로그인된 회원번호,auth 구하기
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		//<3> selectReply()메서드를 통해, 댓글 데이터 전부 뽑아와서 댓글작성자 회원번호 구하기
		OqnaReplyVO db_reply = oqnaService.selectReply(oqnaReplyVO.getQnare_num());
		
		//<4> 로그인 되어있지 않은경우
		if(user_num==null) {
			map.put("result","logout");
			
		//<5> 관리자가 아닌 경우
		}else if(user_auth<3){
			map.put("result","notAdmin");
			
		//<6> 로그인회원번호와 작성자 회원번호가 일치하는 경우	
		}else if(user_num!=null && user_num==db_reply.getUser_num()){
			//댓글 수정
			oqnaService.updateReply(oqnaReplyVO);
			map.put("result","success");
			
		//<7> 로그인 회원번호와 작성자 회원번호가 불일치한 경우	
		}else {
			map.put("result","wrongAccess");
		}
		//<8> Map객체 반환
		return map;
	}
	
	
	//댓글 삭제
	@RequestMapping("/oqna/oqnaDeleteReply.do")
	@ResponseBody
	public Map<String,String> deleteReply(@RequestParam int re_num,HttpSession session){
		
		//(1) Map 객체 생성
		Map<String,String> map = new HashMap<String,String>();
		
		//(2) 로그인한 회원번호와 작성자 회원번호가 일치하는지 확인
		//1. session으로부터 user_num, auth 받아오기
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		//2. selectReply()으로부터 한건의 데이터 받아오기  ==> 로그인한(user_num)과 댓글 작성자(re_num)이 일치하는지 확인필요
		OqnaReplyVO db_reply = oqnaService.selectReply(re_num);
				
		//3.로그인이 되지 않은 경우
		if(user_num ==null) {
			map.put("result","logout");
		}
		//4. 관리자가 아닌 경우
		else if(user_auth<3){
			map.put("result","notAdmin");
		}
		//5. 로그인이 되어있고, 로그인한 회원번호와 작성자 회원번호 일치
		else if(user_num!=null && user_num==db_reply.getUser_num()) {
			oqnaService.deleteReply(re_num);	//삭제처리
			map.put("result","success");
		}
		//6. 로그인한 회원번호와 작성자 회원번호 불일치
		else {
			map.put("result","wrongAccess");
		}
		return map;
	}
}
