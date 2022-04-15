package kr.spring.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.kit.service.KitService;
import kr.spring.kit.vo.KitVO;
import kr.spring.offclass.service.OffclassService;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.service.OnclassService;
import kr.spring.onclass.vo.OnclassVO;

@Controller
public class SearchController {

	@Autowired
	private OnclassService onclassService;
	
	@Autowired
	private OffclassService offclassService;
	
	@Autowired
	private KitService kitService;
	
	@RequestMapping("/search/total_search.do")
	public String search(@RequestParam String keyword,Model model) {
		
		System.out.println("keyword:" + keyword);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield","0");
		map.put("keyword",keyword);
		map.put("category",0);
		map.put("sort",0);
		map.put("start",1);
		map.put("end", 50);
		
		//온라인 검색
		int on_count = onclassService.selectRowCount(map);
		List<OnclassVO> onList = null;
		if(on_count > 0) {
			onList = onclassService.selectList(map);
			for(int i=0;i<onList.size();i++) {
				OnclassVO onclassVO = onList.get(i);
				int like_count = onclassService.selectLikeCount(onclassVO.getOn_num());
				onclassVO.setLike_count(like_count);
			}
		}
		
		System.out.println("onList:" + onList);
		
		//오프라인 검색
		int off_count = offclassService.selectRowCount(map);
		List<OffclassVO> offList = null;
		if(off_count > 0) {
			offList = offclassService.selectListOffClass(map);
			for(int i=0;i<offList.size();i++) {
				OffclassVO offclassVO = offList.get(i);
				offclassVO.setRating_percent(offclassVO.getRating()*20);
			}
		}
		
		System.out.println("offList:" + offList);
		
		//키트 검색
		int kit_count = kitService.selectRowCount(map);
		
		map.put("start",1);
		map.put("end",50);
		
		List<KitVO> kitList = null;
		if(kit_count > 0) {
			kitList = kitService.selectList(map);
		}
		
		System.out.println("kitList:" + kitList);
		System.out.println("on_count:" + on_count);
		System.out.println("off_count:" + off_count);
		System.out.println("kit_count:" + kit_count);
		
		model.addAttribute("on_count", on_count);
		model.addAttribute("onList", onList);
		model.addAttribute("off_count", off_count);
		model.addAttribute("offList", offList);
		model.addAttribute("kit_count", kit_count);
		model.addAttribute("kitList", kitList);
		
		return "total_search";
	}
	
}
