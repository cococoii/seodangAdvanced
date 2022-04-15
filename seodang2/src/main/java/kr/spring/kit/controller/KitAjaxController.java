package kr.spring.kit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.kit.service.KitService;
import kr.spring.kit.vo.KitLikeVO;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class KitAjaxController {
	
	private static final Logger logger = LoggerFactory.getLogger(KitAjaxController.class);
	
	private int rowCount = 10;
	
	@Autowired
	private KitService kitService;
	
	//찜하기
	@RequestMapping("/kit/like.do")
	@ResponseBody
	public Map<String,Object> likeForm(int kit_num,HttpSession session){
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println(kit_num);
		
		//로그인이 되지 않은 경우
		if(user_num==null) {
			map.put("result","logout");
		}else {
			KitLikeVO vo = kitService.selectLike(user_num, kit_num);
			if(vo!=null) {//이미 추천한 경우
				kitService.deleteLike(vo.getKitLike_num());
				map.put("result", "success");
				map.put("status", "noFav");
				map.put("count", kitService.selectLikeCount(kit_num));
			}else{
				kitService.insertLike(user_num,kit_num);
				map.put("result", "success");
				map.put("status", "yesFav");
				map.put("count",kitService.selectLikeCount(kit_num));
				}
		}
		return map;

	}
	//게시물 들어갔을때 하트 빈하트 여부
	@RequestMapping("/kit/getFav.do")
	@ResponseBody	
	public Map<String, Object> execute(int kit_num,HttpSession session,KitLikeVO kitLikeVO) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		if(user_num == null) {//로그인 안됨
			map.put("result", "success"); //좋아요 표시
			map.put("status", "noFav"); //빈 하트 표시
			map.put("count", kitService.selectLikeCount(kit_num)); //좋아요 총 개수 표시
		}else {	
			kitLikeVO = kitService.selectLike(user_num, kit_num);
			
			if(kitLikeVO!=null) { //vo가 null이 아니면 추천을 이미 한 경우
				map.put("result", "success");
				map.put("status", "yesFav"); // 추천하트로 표시
				map.put("count", kitService.selectLikeCount(kit_num)); //좋아요 총 개수 표시
			}else{ // 추천을 안한 경우
				map.put("result", "success");
				map.put("status", "noFav"); //빈 하트 표시 
				map.put("count", kitService.selectLikeCount(kit_num)); //좋아요 총 개수 표시
			}
		}
		return map;
	}
}

