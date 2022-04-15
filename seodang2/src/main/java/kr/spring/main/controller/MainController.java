package kr.spring.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.kit.service.KitService;
import kr.spring.kit.vo.KitVO;
import kr.spring.main.service.MainService;
import kr.spring.offclass.service.OffclassService;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.service.OnclassService;
import kr.spring.onclass.vo.OnclassVO;
import kr.spring.util.PagingUtil;

  
@Controller
public class MainController {
	@Autowired
	private MainService mainService;
	@Autowired
	private OnclassService onclassService;
	@Autowired
	private OffclassService offclassService;
	@Autowired
	private KitService kitService;

	@RequestMapping("/main/main.do")
	public ModelAndView mainPage(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
									HttpSession session){
				
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_num", user_num);
		
		int on_count = onclassService.selectRowCount(map);
		
		PagingUtil pageOn = new PagingUtil(currentPage,on_count,4,10,"main.do");
		
		map.put("start",pageOn.getStartCount());
		map.put("end", pageOn.getEndCount());
		
		List<OnclassVO> onList = null;
		if(on_count > 0) {
			onList = mainService.selectListOnMain(map);
			for(int i=0;i<onList.size();i++) {
				OnclassVO onclassVO = onList.get(i);
				int like_count = onclassService.selectLikeCount(onclassVO.getOn_num());
				onclassVO.setLike_count(like_count);
				onclassVO.setRating_percent(onclassVO.getRating()*20);
				System.out.println("온레이팅 퍼센트 : " + onclassVO.getRating_percent());
			}
		}
		
		int off_count = offclassService.selectRowCount(map);
		PagingUtil pageOff = new PagingUtil(currentPage,off_count,4,10,"main.do");
		
		map.put("start",pageOff.getStartCount());
		map.put("end", pageOff.getEndCount());
		
		List<OffclassVO> offList = null;
		if(off_count > 0) {
			offList = mainService.selectListOffMain(map);
			for(int i=0;i<offList.size();i++) {
				OffclassVO offclassVO = offList.get(i);
				int like_count = offclassService.selectLikeCount(offclassVO.getOff_num());
				offclassVO.setLike_sum(like_count);
				offclassVO.setRating_percent(offclassVO.getRating()*20);
				System.out.println(offclassVO.getRating_percent());
				System.out.println("레이팅 퍼센트 : " + offclassVO.getRating_percent());
			}
		}
		int kit_count = kitService.selectRowCount(map);
		
		PagingUtil pageKit = new PagingUtil(currentPage,kit_count,4,10,"main.do");
		
		map.put("start",pageKit.getStartCount());
		map.put("end", pageKit.getEndCount());
		
		List<KitVO> kitList = null;
		if(kit_count > 0) {
			kitList = kitService.selectList(map);
			for(int i=0;i<kitList.size();i++) {
				KitVO kitVO = kitList.get(i);
				int like_count = kitService.selectLikeCount(kitVO.getKit_num());
				System.out.println("키트카운트"+like_count);
				kitVO.setLike_count(like_count);
				
			}
		}
		
		//인기있는 클래스
		List<OffclassVO> onListRank = null;
		int rankCount = mainService.selectRowCount(map);
		

		if(rankCount > 0) {
			onListRank = mainService.selectListRank(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main");
		
		mav.addObject("onList",onList);
		mav.addObject("onListRank",onListRank);
		mav.addObject("pagingHtmlOn", pageOn.getPagingHtml());
		mav.addObject("offList",offList);
		mav.addObject("pagingHtmlOff", pageOff.getPagingHtml());
		mav.addObject("kitList",kitList);
		mav.addObject("pagingHtmlKit", pageKit.getPagingHtml());		
		
		return mav;
		
	}
}


