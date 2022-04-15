package kr.spring.onclass.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.onclass.service.OnclassService;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.user.controller.UserController;
import kr.spring.util.PagingUtil;



@Controller
public class OnclassAjaxController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private int rowCount = 10;
	
	@Autowired
	private OnclassService onclassService;
	
	@RequestMapping("/onclass/like.do")
	@ResponseBody	
	public Map<String, Object> likeForm(int on_num,HttpSession session) {
		Integer user_num = (Integer)session.getAttribute("session_user_num");
	
		Map<String,Object> map = new HashMap<String,Object>();

		if(user_num == null) {//로그인 안됨
			map.put("result", "logout");
		}else {
			OnlikeVO vo = onclassService.selectLike(user_num,on_num);	
		if(vo!=null) { //이미 추천을 한 경우
			onclassService.deleteLike(vo.getOnlike_num());
			map.put("result", "success");
			map.put("status", "noFav");
			map.put("count",onclassService.selectLikeCount(on_num));
		}else{
			onclassService.insertLike(user_num,on_num);
			map.put("result", "success");
			map.put("status", "yesFav");
			map.put("count",onclassService.selectLikeCount(on_num));
			}
		}
		return map; 
	}
	
	//게시물 들어갔을때 하트 빈하트 여부
	@RequestMapping("/onclass/getFav.do")
	@ResponseBody	
	public Map<String, Object> execute(int on_num,HttpSession session,OnlikeVO onlikeVO) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		if(user_num == null) {//로그인 안됨
			map.put("result", "success"); //좋아요 표시
			map.put("status", "noFav"); //빈 하트 표시
			map.put("count", onclassService.selectLikeCount(on_num)); //좋아요 총 개수 표시
		}else {	
			onlikeVO = onclassService.selectLike(user_num, on_num);
			
			if(onlikeVO!=null) { //vo가 null이 아니면 추천을 이미 한 경우
				map.put("result", "success");
				map.put("status", "yesFav"); // 추천하트로 표시
				map.put("count", onclassService.selectLikeCount(on_num)); //좋아요 총 개수 표시
			}else{ // 추천을 안한 경우
				map.put("result", "success");
				map.put("status", "noFav"); //빈 하트 표시 
				map.put("count", onclassService.selectLikeCount(on_num)); //좋아요 총 개수 표시
			}
		}
		return map;
	}
}
