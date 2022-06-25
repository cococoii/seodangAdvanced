package kr.spring.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import kr.spring.qna.service.OqnaService;
import kr.spring.qna.vo.OqnaVO;
import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;
import kr.spring.util.AuthBlockException;
import kr.spring.util.AuthCheckException;
import kr.spring.util.PagingUtil;

@Controller
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private OqnaService oqnaService;

	@ModelAttribute
	public UserVO initCommand() {
		return new UserVO();
	}

	//**회원 가입 ,로그인 작성 시작 ** - <임소연>
	// 회원 등록 폼 호출
	@GetMapping("/user/registerUser.do")
	public String form() {
		return "userRegister";
	}

	// 회원등록 처리
	@PostMapping("/user/registerUser.do")
	public String submit(@Valid UserVO userVO, BindingResult result) {

		logger.info("<<회원 가입>> :" + userVO);

		if (result.hasErrors()) {
			return form();
		}

		userService.insertUser(userVO);

		return "redirect:/main/main.do";
	}

	// 로그인 폼
	@GetMapping("/user/login.do")
	public String formLogin() {
		return "userLogin";
	}

	// 로그인 폼에서 전송된 데이터 처리
	@PostMapping("/user/login.do")
	public String submitLogin(@Valid UserVO userVO, BindingResult result, HttpSession session) {

		logger.info("<<회원 로그인>> : " + userVO);

		// id와 password 필드만 체크
		if (result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formLogin();
		}

		// 로그인 체크(id,비밀번호 일치 여부 체크)
		try {
			UserVO user = userService.selectCheckUser(userVO.getId());

			boolean check = false;
			if (user != null) {
				// 비밀번호 일치 여부 체크 //사용자가 입력한 비밀번호
				check = user.isCheckedPassword(userVO.getPasswd());
			}
			if (user.getAuth() == 0) { // 탈퇴회원의 경우 - 아이디만 확인 후
				throw new AuthCheckException();
			} else {
				if (user.getAuth() == 1 && check) {// 정지회원의 경우 - 아이디, 비번 확인 후
					throw new AuthBlockException();
				}
				if (check) {
					// 인증 성공, 로그인 처리
					session.setAttribute("session_user_num", user.getUser_num());
					session.setAttribute("session_user_name", user.getName());
					session.setAttribute("session_user_auth", user.getAuth());
					session.setAttribute("session_user_photo", user.getPhoto());

					return "redirect:/main/main.do";
				}
			}

			throw new Exception();
		} catch (AuthCheckException e) {// 로그인 오류
			// 인증 실패로 로그인 폼을 호출
			result.reject("WithdrawlAccount");
			// 인증 실패로 로그인 폼 호출
			return formLogin();
		} catch (AuthBlockException e) {// 정지회원의 경우
			result.reject("blockAccount");

			return formLogin();
		} catch (Exception e) {
			result.reject("invalidIdOrPassword");

			return formLogin();
		}
	}

	// 회원 로그아웃
	@RequestMapping("/user/logout.do")
	public String processLogout(HttpSession session) {

		// 로그아웃
		session.invalidate();

		return "redirect:/main/main.do";
	}
	
	//회원 가입,로그인 끝 
	
	// 내 메뉴 보기
	@RequestMapping("/user/myMenu.do")
	public String processMenu(HttpSession session, Model model) {
		Integer user_num = (Integer) session.getAttribute("session_user_num");
		UserVO user = userService.selectUser(user_num);

		logger.info("<<마이페이지>> : " + user);

		model.addAttribute("user", user);
		
		return "userMenu";
	}
	
	//전체qna 내가 쓴 글
	@RequestMapping("/user/myoqnaList.do")
	public String test2(HttpSession session, Model model) {
		
		Integer user_num = (Integer) session.getAttribute("session_user_num");
		UserVO user = userService.selectUser(user_num);

		logger.info("<<회원 상세 정보>> : " + user);

		model.addAttribute("user", user);

		// QNA, 데이터를 맵에 넘기기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyfield", "");
		map.put("keyword", "");
		map.put("user_num", user_num);
		int count = oqnaService.selectOqnaRowCount(map);

		logger.info("<<count>> : " + count);
		
		// <3> 페이지처리
		PagingUtil page = new PagingUtil("", "", 1, count, 5, 10, null);
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());

		// <4> map을 넘겨줘서 글목록 뽑아오기
		List<OqnaVO> list = null;
		if (count > 0) {
			list = oqnaService.getOqnaList(map);
		}

		// <5> request에 데이터 저장
		model.addAttribute("list", list);

		return "myoqnaList";
	}
	
	//myqna리스트
	@RequestMapping("/user/myqnaList.do")
	public String processmyoqna(HttpSession session, Model model) {
		
		
		return "myqnaList";
	}

	// 마이페이지
	@RequestMapping("/user/myPage.do")
	public String process(HttpSession session, Model model) {

		Integer user_num = (Integer) session.getAttribute("session_user_num");
		UserVO user = userService.selectUser(user_num);

		logger.info("<<회원 상세 정보>> : " + user);

		model.addAttribute("user", user);

		return "userView";
	}

	// 수정폼
	@GetMapping("/user/update.do")
	public String formUpdate(HttpSession session, Model model) {
		Integer user_num = (Integer) session.getAttribute("session_user_num");

		UserVO userVO = userService.selectUser(user_num);

		model.addAttribute("userVO", userVO);

		return "userModify";
	}

	// 수정폼에서 전송된 데이터 처리
	@PostMapping("/user/update.do")
	public String submitUpdate(@Valid UserVO userVO, BindingResult result, HttpSession session) {
		logger.info("<<회원 정보 수정>> : " + userVO);

		// 유효성 체크 결과 오류가 있으면 폼 호출
		if (result.hasErrors()) {
			return "userModify";
		}

		Integer user_num = (Integer) session.getAttribute("session_user_num");
		userVO.setUser_num(user_num);

		// 회원정보수정
		userService.updateUser(userVO);

		return "redirect:/user/myPage.do";
	}

	// 비밀번호 변경 폼
	@GetMapping("/user/changePassword.do")
	public String formChangePassword() {
		return "userChangePassword";
	}

	// 비밀번호 변경 폼에서 전송된 데이터 처리
	@PostMapping("/user/changePassword.do")
	public String submitChangePassword(@Valid UserVO userVO, BindingResult result, HttpSession session) {

		logger.info("<<비밀번호 변경 처리>> : " + userVO);

		// 유효성 체크 결과 오류가 있으면 폼 호출
		if (result.hasFieldErrors("now_passwd") || result.hasFieldErrors("passwd")) {
			return formChangePassword();
		}

		Integer user_num = (Integer) session.getAttribute("session_user_num");
		userVO.setUser_num(user_num);

		// 세션에 저장된 회원번호를 이용해서 DB에 저장된 회원번호를 UserVO에 담아서 반환
		UserVO db_user = userService.selectUser(userVO.getUser_num());
		// DB에서 읽어온 비밀번호 //사용자가 입력한 비밀번호
		if (!db_user.getPasswd().equals(userVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPassword");
			return formChangePassword();
		}

		// 비밀번호 수정
		userService.updatePassword(userVO);

		return "redirect:/user/myPage.do";
	}

	// 회원 삭제 폼
	@GetMapping("/user/delete.do")
	public String formDelete() {
		return "userDelete";
	}

	// 회원 데이터 삭제
	@PostMapping("/user/delete.do")
	public String submitDelete(@Valid UserVO userVO, BindingResult result, HttpSession session) {
		logger.info("<<회원탈퇴>> : " + userVO);

		// 유효성 체크 결과 오류가 있으면 폼 호출
		if (result.hasFieldErrors("id") || result.hasFieldErrors("passwd")) {
			return formDelete();
		}

		Integer user_num = (Integer) session.getAttribute("session_user_num");
		userVO.setUser_num(user_num);

		// 아이디와 비밀번호 일치 여부 체크
		try {
			// 세션에 저장된 회원번호를 이용해서 DB에 저장된 회원 정보를 UserVO에 담아서
			// 반환
			UserVO db_user = userService.selectUser(userVO.getUser_num());
			boolean check = false;

			if (db_user != null && userVO.getId().contentEquals(db_user.getId())) {
				// 비밀번호 일치 여부 체크
				check = db_user.isCheckedPassword(userVO.getPasswd());
			}
			if (check) {
				// 인증 성공, 회원정보 삭제
				userService.deleteUser(userVO.getUser_num());
				// 로그아웃
				session.invalidate();

				return "redirect:/main/main.do";
			}
			// 인증 실패
			throw new AuthCheckException();
		} catch (AuthCheckException e) {
			result.reject("invalidIdOrPassword");
		}

		return "redirect:/main/main.do";
	}

	// 이미지 출력
	@RequestMapping("/user/photoView.do")
	public ModelAndView viewImage(HttpSession session) {

		Integer user_num = (Integer) session.getAttribute("session_user_num");
		UserVO userVO = userService.selectUser(user_num);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile", userVO.getPhoto());
		mav.addObject("filename", userVO.getPhoto_name());

		return mav;
	}
}
