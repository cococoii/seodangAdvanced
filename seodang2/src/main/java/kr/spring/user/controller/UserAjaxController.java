package kr.spring.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;

@Controller
public class UserAjaxController {
private static final Logger logger = LoggerFactory.getLogger(UserAjaxController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/confirmId.do")
	@ResponseBody
	public Map<String, String> process(@RequestParam String id){
		
		logger.info("<<id>> : "+id);
		
		Map<String, String> map = new HashMap<String, String>();
		
		UserVO user= userService.selectCheckUser(id);
		//경우의 수 세가지 
		if(user!=null) {
			//아이디 중복 
			map.put("result", "idDuplicated");
		}else {
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", id)) {
				//패턴 불일치
				map.put("result", "notMatchPattern");
			}else {
				//아이디 미중복
				map.put("result", "idNotFound");
			}
		}
		return map;
	}
	
	@RequestMapping("/user/updateMyPhoto.do")
	@ResponseBody
	public Map<String,String> processProfile(UserVO userVO,
										HttpSession session){
		Map<String,String> map = new HashMap<String,String>();
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			map.put("result", "logout");
		}else {//로그인 된 경우
			userVO.setUser_num(user_num);
			userService.updateProfile(userVO);
			
			//이미지를 업로드한 후 세션에 저장된 user_photo 값 변경
			session.setAttribute("user_photo", userVO.getPhoto());
			
			map.put("result","success");
		}
		
		return map;
	}
}
