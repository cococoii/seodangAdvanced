package kr.spring.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;
import kr.spring.util.PagingUtil;

@Controller
public class UserAdminController {
	private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);

	@Autowired
	private UserService userService;

	// 회원 관리 목록
	@RequestMapping("/user/admin_list.do")
	public ModelAndView process(@RequestParam(value = "pageNum", defaultValue = "1") 
								int currentPage,
								@RequestParam(value = "keyfield", defaultValue = "") 
								String keyfield,
								@RequestParam(value = "keyword", defaultValue = "") 
								String keyword) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);

		// 총 글의 갯수 또는 검색된 글의 갯수
		int count = userService.selectRowCount(map);

		logger.info("<<회원관리>> : " + count);

		// 페이지 처리
		PagingUtil page = new PagingUtil(
				keyfield, keyword, currentPage, count, 20, 10, "admin_list.do");

		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());

		List<UserVO> list = null;
		if (count > 0) {
			list = userService.selectList(map);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin_userList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());

		return mav;
	}

	//회원 정보 수정 폼
	@GetMapping("/user/admin_update.do")
	public String form(@RequestParam int user_num, Model model) {
		UserVO userVO = userService.selectUser(user_num);
		
		model.addAttribute("userVO", userVO);
		
		return "admin_userModify";
	}
	
	//수정 폼에서 전송된 데이터 처리
	@PostMapping("/user/admin_update.do")
	public String submit(@Valid UserVO userVO,
						BindingResult result) {
		logger.info("<<관리자 회원수정 처리>> : " + userVO );
		
		//유효성 체크 결과 오류가 발생하면 폼 호출
		if(result.hasErrors()) {
			return "admin_userModify";
		}
		
		//회원정보 수정
		userService.updateByAdmin(userVO);
		
		return "redirect:/user/admin_list.do";
	}

}
