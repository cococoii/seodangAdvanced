package kr.spring.admin.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.admin.service.AdminService;
import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;
import kr.spring.util.PagingUtil;



@Controller
public class AdminController {

	// 로그처리 준비
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	//(1) 의존관계 주입
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	//(2) 관리자 - 회원관리 목록
	@RequestMapping("/admin/adminMemberList.do")
	public ModelAndView process(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
								@RequestParam(value="keyfield", defaultValue="") String keyfield,
								@RequestParam(value="keyword", defaultValue="") String keyword) {
	
		//<1> Map객체 생성 -> keyfield, keyword 전달
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield",keyfield);
		map.put("keyword",keyword);
		
		//<2> 총 글의 갯수 또는 검색된 글의 갯수
		int count = adminService.selectRowCount(map);
		logger.info("<<회원관리 목록 - 총 글의 갯수> : " + count);
	
		//<3> 페이지처리
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,20,10,"adminMemberList.do");
		map.put("start",page.getStartCount());
		map.put("end",page.getEndCount());
		
		//<4> selectList메서드에서 반환된 정보를 list객체에 저장
		List<UserVO> list = null;
		if(count>0) {
			list = adminService.selectList(map);
		}
		
		//<5> request에 저장
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminMemberList");//tiles설정
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("pagingHtml",page.getPagingHtml());
		
		return mav;
	}
	
	//(3) 관리자 - 회원정보 수정 form
	@GetMapping("/admin/adminUpdateUser.do")
	public String form(@RequestParam int user_num, Model model) {
		
		//<1>회원정보 불러오기
		UserVO userVO = userService.selectUser(user_num);
		//<2>request에 저장
		model.addAttribute("userVO", userVO);
		
		return "adminModifyUser";
	}
	
	//(4) 관리자 - 회원정보 수정폼에서 전송된 데이터 처리
	@PostMapping("/admin/adminUpdateUser.do")
	public String submit(@Valid UserVO userVO, BindingResult result, 
						Model model, HttpServletRequest request) {
		
		//로그처리
		logger.info("<<관리자 회원수정 처리 >> :"+userVO);
		
		//유효성 체크결과 오류가 발생하면 폼 호출
		if(result.hasErrors()) {
			return "adminModifyUser";
		}
		//유효성 체크결과 오류가 발생하지 않으면 => 회원정보 수정
		adminService.updateMemberByAdmin(userVO);
		
		//alert에 표시할 메시지와 이동할 url 지정
		model.addAttribute("message", "회원정보 수정 완료");
		model.addAttribute("url", request.getContextPath()+"/admin/adminMemberList.do");
		
		
		return "common/resultView";
	}
	// 이미지 출력
		@RequestMapping("/admin/photoView.do")
		public ModelAndView viewImage(@RequestParam int user_num) {

			UserVO vo =  userService.selectUser(user_num);
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("imageView");
			mav.addObject("imageFile", vo.getPhoto());
			mav.addObject("filename", vo.getPhoto_name());

			return mav;
		}
	
}

