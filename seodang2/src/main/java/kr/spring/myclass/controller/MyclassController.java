package kr.spring.myclass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.myclass.service.MyclassService;
import kr.spring.myclass.vo.MyclassVO;
import kr.spring.myclass.vo.PaymentVO;
import kr.spring.onclass.service.OnclassService;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.qna.vo.OqnaVO;
import kr.spring.user.controller.UserController;
import kr.spring.util.PagingUtil;

@Controller
public class MyclassController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MyclassService myclassService;
	@Autowired
	private OnclassService onclassService;
	
	@GetMapping("/myclass/myclassMain.do")
	public String mainForm() {
		return "myclassMain";
	}
	
	@RequestMapping("/myclass/myclassList.do")
	public ModelAndView classList(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage,HttpSession session) {
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", user_num);
		
		int count = myclassService.selectRowCount(map);
		
		PagingUtil page = new PagingUtil(currentPage,count,6,10,"myclassList.do");
		map.put("user_num", user_num);
		map.put("start",page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<MyclassVO> list = null;
			if(count > 0) {
				list = myclassService.selectList(map);
			}  
			
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myclassList");
		mav.addObject("user_num",user_num);
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;
	}
	
	//???????????? ????????? ????????? ??????
	@GetMapping("/myclass/myclassData.do")
	public ModelAndView dataForm(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
							@RequestParam(value="keyfield", defaultValue="") String keyfield,
							@RequestParam(value="keyword", defaultValue="") String keyword,
							HttpSession session,int on_num) {
		
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		
		System.out.println("???????????? ???????????? : "+on_num);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("on_num", on_num);
		
		int count = myclassService.buySelectRowCount(map);
		
		//??????
		System.out.println("????????? ?????? ??? ???: "+on_num);
		
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,8,10,"myclassData.do");
		map.put("start", page.getStartCount());	
		map.put("end", page.getEndCount());
		
		List<PaymentVO> list = null;
		if(count >0) {
			list = myclassService.buyerSelectList(map);
		}
		logger.info("!!????????? ????????? ??????!! : " + list);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myclassData");
		mav.addObject("user_num",session_user_num);
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("people",myclassService.peopleCount(on_num));
		mav.addObject("pagingHtml",page.getPagingHtml());
		
		return mav;
	}
	
	//???????????? ?????? ???????????? 
	@RequestMapping("/myclass/myclassDelete.do")
	public String myclassDelete(int onreg_num,int on_num,int user_num) {
		
		myclassService.myclassDelete(onreg_num, user_num);
		
		return "redirect:/myclass/myclassData.do?on_num="+on_num;
	}
	//???????????? ?????? ?????? ?????????
	@RequestMapping("/myclass/myclassUpdate.do")
	public String myclassUpdate(int onreg_num,int on_num,int user_num) {
		myclassService.myclassUpdate(onreg_num, user_num);
		
		return "redirect:/myclass/myclassData.do?on_num="+on_num;
	}
	//??????????????? ?????? ?????? ?????????
	@RequestMapping("/myclass/myclassUserDelete.do")
	public String myclassUserDelete(int onreg_num,int on_num) {
		myclassService.myclassUserDelete(onreg_num);
		
		return "redirect:/myclass/myclassData.do?on_num="+on_num;
	}
	
	
	@RequestMapping("/myclass/likeList.do")
	public ModelAndView likeForm(@RequestParam(value="pageNum",defaultValue="1")
											int currentPage, HttpSession session) {
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", user_num);
		
		int count = myclassService.selectRowCount3(map);
		
		PagingUtil page = new PagingUtil(currentPage,count,6,10,"likeList.do");
		map.put("start",page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<OnlikeVO> list = null;

		if(count > 0) {
			list = myclassService.selectLikeList(map);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("likeList");
		mav.addObject("user_num",user_num);
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml", page.getPagingHtml());

		return mav;	
	}
	
	
	  //??????????????? ??? ????????????
	  @PostMapping("/myclass/likeDelete.do") 
	  public String deleteLike(int on_num,int onoff,Model model,HttpServletRequest request) {
	  
	  //System.out.println("?????? ??????  : "+on_num);
	  //System.out.println("?????? ?????????  : "+onoff);
	  
	  if(onoff == 1) {
		  myclassService.onDeleteLike(on_num);
	  }else if(onoff == 2) {
		  myclassService.offDeleteLike(on_num);
	  }
		  
	  model.addAttribute("message", "????????? ?????? ??????"); 
	  model.addAttribute("url", request.getContextPath() + "/myclass/likeList.do");
	  
	  return "common/resultView"; 
	  }	
	  
	  //
}
