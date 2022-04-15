package kr.spring.offclass.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.offclass.service.OffclassService;
import kr.spring.offclass.vo.OffTimetableVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.offclass.vo.OfflikeVO;
import kr.spring.offclass.vo.OffstarVO;
import kr.spring.offclass.vo.UploadFileOffVO;
import kr.spring.onclass.vo.OnclassVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.user.service.UserService;
import kr.spring.user.vo.UserVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

@Controller
public class OffclassController {
	
	private Logger logger = LoggerFactory.getLogger(OffclassController.class);
	
	@Autowired
	private OffclassService offclassService;
	
	@Autowired
	private UserService userService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public OffclassVO initCommand() {
		return new OffclassVO();
	}
	
	//오프라인 클래스 폼
	@GetMapping("/offclass/offclassOpen.do")
	public String form(HttpSession session) {
		ArrayList<OffTimetableVO> list  =(ArrayList<OffTimetableVO>)session.getAttribute("list");
		session.removeAttribute("list");
		return "offclassOpen";
	}
	
	//오프라인 클래스 등록
	@PostMapping("/offclass/offclassOpen.do")
	public String sumbit(@Valid OffclassVO offclassVO, BindingResult result,HttpSession session,
										MultipartHttpServletRequest mhsq) throws IllegalStateException, IOException {
		
		logger.info("<<오프라인 클래스 등록>>: "+offclassVO);
		
		int off_num = offclassService.selectOff_num();
		
		String uploadFolder = "/resources/image_upload";
		String realFolder = session.getServletContext().getRealPath(uploadFolder);
		
		System.out.println(realFolder);
		
        File dir = new File(realFolder);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        
        String mimage = null; 
        //넘어온 파일을 리스트로 저장
        List<MultipartFile> mf = mhsq.getFiles("uploadfileoff");
        if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {       
        } else {
            for (int i = 0; i < mf.size(); i++) {
                //파일 중복명 처리
                String genId = UUID.randomUUID().toString();
                //본래 파일명
                String originalfileName = mf.get(i).getOriginalFilename();                
                String extension = StringUtils.getFilenameExtension(originalfileName);
                //저장되는 파일 이름 
                String saveFileName = genId + "." + extension;
                //저장 될 파일 경로
                String savePath = realFolder +"/"+ saveFileName;
                //파일 사이즈
                long fileSize = mf.get(i).getSize(); 
                //파일 저장
                mf.get(i).transferTo(new File(savePath)); 
					
				if(i == 0) {mimage = saveFileName;}
					 
				System.out.println("1 : " + originalfileName);
				System.out.println("2 : " + saveFileName);
				System.out.println("3 : " + fileSize);
				System.out.println("4 : " + off_num);
				
				offclassService.fileUpload(originalfileName, saveFileName, fileSize, off_num);
            }
        }
        
		
		if(result.hasErrors()) {
			return "offclassOpen";
		}
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		//회원번호 세팅
		offclassVO.setUser_num(user_num);
		
		//이미지
		System.out.println(mimage);
		
		offclassVO.setOff_num(off_num);
		offclassVO.setMimage(mimage);
		
		//글 작성
		ArrayList<OffTimetableVO> list  =(ArrayList<OffTimetableVO>)session.getAttribute("list");
		//실험
		offclassVO.setOff_num(off_num);
		offclassService.insertOffClass(offclassVO,list);
		session.removeAttribute("list");
		 
		return "redirect:/offclass/offclassList.do";
	}
	
	//오프라인 클래스 목록
	@RequestMapping("/offclass/offclassList.do")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue = "1") int currentPage,@RequestParam(value="sort",defaultValue="0") int sort,@RequestParam(value="category_num",defaultValue="0") int category_num) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("sort", sort);
		map.put("category_num",category_num);
		System.out.println(sort);
		int count = offclassService.selectRowCount(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(currentPage, count, 20, 10, "offclassList.do");
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		List<OffclassVO> list =null;
		if(count>0) {
			list = offclassService.selectListOffClass(map);
			for(int i=0;i<list.size();i++) {
				OffclassVO offclassVO = list.get(i);
				//int like_count = offclassService.selectLikeCount(offclassVO.getOff_num());
				//offclassVO.setLike_count(like_count);
				offclassVO.setRating_percent(offclassVO.getRating()*20);
			}
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("offclassList");
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("sort",sort);
		mav.addObject("category_num",category_num);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;
	}
	
	//게시판 글 상세
	@RequestMapping("/offclass/offclassDetail.do")
	public ModelAndView processdetail(@RequestParam int off_num,HttpSession session) {
		Integer user_num= (Integer)session.getAttribute("session_user_num");
		Map<String, Object> map = new HashMap<String, Object>();
		
		//다중업로드 파일 가져오기 시작
		List<UploadFileOffVO> uplist = offclassService.selectFileOff(off_num);
		ModelAndView mav = new ModelAndView();
		logger.info("<<게시판 글 상세 - 글 번호>>: "+off_num);

		if(user_num!=null) {
			OfflikeVO offLikeVO=offclassService.selectLike(user_num, off_num);
			if(offLikeVO==null) {
				mav.addObject("likecheck",0);
			}else {
				mav.addObject("likecheck",offLikeVO.getOlike());
			}
		}
		map.put("off_num",off_num);
		List<OffTimetableVO> list = offclassService.selectListOffTimeDate(off_num);
		String[] week = {"일","월","화","수","목","금","토"};
		
		OffclassVO offclass=offclassService.selectOffClass(off_num);
		
		for(int i=0;i<list.size();i++) {
			String date = list.get(i).getTime_date().toString().substring(5).replace("-", "/");
			OffTimetableVO offTimetableVO = list.get(i);
			offTimetableVO.setString_date(date);
			Calendar cal = Calendar.getInstance() ;
			cal.setTime(list.get(i).getTime_date());
			int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
			offTimetableVO.setDay(week[dayNum-1]);
			
		}
		//리뷰 개수
		int review_count = offclassService.selectRowReviewCount(map);
		//별점 점수
		float rating = 0;
		if(review_count>0) {
			rating= offclassService.selectReviewRating(off_num);
		}
		
		//후기 목록 받아오기
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("off_num", off_num);
		map2.put("rownum",6);
		List<OffstarVO> list2 = offclassService.selectListOffReview(map2);
		//별점 점수 세팅하기
		for(int i=0;i<list2.size();i++) {
			OffstarVO offstarVO = list2.get(i);
			System.out.println("후기 목록 보여주기"+offstarVO);
			offstarVO.setRating_percent(offstarVO.getRating()*20);
		}
		
		offclass.setOff_name(StringUtil.useNoHtml(offclass.getOff_name()));
		mav.addObject("offclass", offclass);
		mav.addObject("list", list);
		mav.setViewName("offclassDetail");
		mav.addObject("rating",rating);
		mav.addObject("review_count",review_count);
		mav.addObject("list2",list2);
		//다중 업로드
		mav.addObject("uplist",uplist);
		for(int i=0; i<uplist.size();i++) {
			mav.addObject("upfile"+i,uplist.get(i));
		}
		mav.addObject("size", uplist.size());
		return mav;
	}
	//이미지 보이기- 오프라인 클래스
	@RequestMapping("/offclass/imageView.do")
	public ModelAndView viewImage(@RequestParam int off_num) {
		OffclassVO offclass = offclassService.selectOffClass(off_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile",offclass.getOff_uploadfile());
		mav.addObject("filename", offclass.getOff_filename());
		return mav;
	}	
	//이미지 보이기 - User
	@RequestMapping("/offclass/imageViewUser.do")
	public ModelAndView viewImageUser(@RequestParam int user_num) {
		UserVO userVO = userService.selectUser(user_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile", userVO.getPhoto());
		mav.addObject("filename", userVO.getPhoto_name());
		return mav;
	}
	//오프라인 클래스 수정폼
	@GetMapping("/offclass/offclassUpdate.do")
	public ModelAndView formUpdate(@RequestParam int off_num,HttpSession session) {
		
		OffclassVO offclassVO = offclassService.selectOffClass(off_num);
		logger.info("<<offclassVO >>"+offclassVO);
		List<OffTimetableVO> list = new ArrayList<OffTimetableVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("off_num", off_num);
		list = offclassService.selectListOffTimetable(map);
		System.out.println(list);
		ModelAndView mav = new ModelAndView();
		if(list!=null) {
			mav.addObject("list", list);
			session.setAttribute("list", list);
		}
		
		
		mav.setViewName("offclassUpdate");
		mav.addObject("offclassVO",offclassVO);
		
		return mav;
	}
	//오프라인 클래스 수정
	@PostMapping("/offclass/offclassUpdate.do")
	public String submitUpdate(@Valid OffclassVO offclassVO,BindingResult result,HttpSession session) {
		
		logger.info("<<offclassVO - off_num>>"+offclassVO);
		
		if(result.hasErrors()) {
			return "offclassUpdate";
		}
		ArrayList<OffTimetableVO> list  =(ArrayList<OffTimetableVO>)session.getAttribute("list");
		offclassService.updateOffClass(offclassVO,list);
		session.removeAttribute("list");
		
		return "redirect:/offclass/offclassDetail.do?off_num="+offclassVO.getOff_num();
	}
	
	//오프라인 클래스 후기 작성 폼 
	@GetMapping("/offclass/offclassReview.do")
	public String reviewForm(OffstarVO offstarVO,int off_num,HttpSession session,Model model) {
		logger.info("<<off_num >>"+offstarVO.getOff_num());
		//int off_num = offstarVO.getOff_num();
		OffclassVO offclassVO = offclassService.selectOffClass(off_num);
		offstarVO.setOff_name(offclassVO.getOff_name());
		
		Integer user_num = (Integer)session.getAttribute("session_user_num");

		//작성했는지 확인을 하기 위해섯
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("off_num",off_num);
		map.put("user_num",user_num);
		//작성했는지 확인하기 위해서
		OffstarVO offstarVO2 = offclassService.selectOffReview(map);
		if(offstarVO2!=null) {
			model.addAttribute("offstarVO",offstarVO2);
			return "redirect:/offclass/offclassReviewUpdate.do?off_num="+off_num;
		}
		
		return "offclassReview";
	}
	
	//오프라인 클래스 후기 작성
	@PostMapping("/offclass/offclassReview.do")
	public String reviewSubmit(@Valid OffstarVO offstarVO,BindingResult result,HttpSession session) {
		int off_num = offstarVO.getOff_num();
		logger.info("<<오프라인 클래스 등록>>: "+off_num);
		System.out.println(offstarVO.getRating());
		
		if(result.hasErrors()) {
			return "offclassReview";
		}
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		//회원번호 세팅
		offstarVO.setUser_num(user_num);
		offclassService.insertOffReview(offstarVO);
		
		return "redirect:/offclass/offclassDetail.do?off_num="+off_num;
	}
	
	//오프라인 클래스 후기 목록
	@RequestMapping("/offclass/offclassReviewList.do")
	public ModelAndView processReview(@RequestParam int off_num,HttpSession session,@RequestParam(value="sort",defaultValue="0") int sort) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("off_num",off_num);
		map.put("sort",sort);
		int count = offclassService.selectRowReviewCount(map);
		OffclassVO offclassVO=offclassService.selectOffClass(off_num);
		int writer_num = offclassVO.getUser_num();
		String photo_name= offclassVO.getPhoto_name();
		String writer_name= offclassVO.getName();//작성자 이름
		Integer session_user_num = (Integer)session.getAttribute("session_user_num");
		
		List<OffstarVO> list = null;
		float rating = 0;
		int star_1 = 0;
		int star_2 = 0;
		int star_3 = 0;
		int star_4 = 0;
		int star_5 = 0;
		ModelAndView mav = new ModelAndView();
		if(count>0) {
			rating= offclassService.selectReviewRating(off_num);
			logger.info("<<rating>>"+rating);
			list = offclassService.selectListOffReview(map);
			map.put("rating",1);
			star_1 = offclassService.selectRowReviewCount(map);
			Map<String, Object> map_2 = new HashMap<String, Object>();
			map_2.put("off_num",off_num);
			map_2.put("rating",2);
			star_2 = offclassService.selectRowReviewCount(map_2);
			Map<String, Object> map_3 = new HashMap<String, Object>();
			map_3.put("off_num",off_num);
			map_3.put("rating",3);
			star_3 = offclassService.selectRowReviewCount(map_3);
			Map<String, Object> map_4 = new HashMap<String, Object>();
			map_4.put("off_num",off_num);
			map_4.put("rating",4);
			star_4 = offclassService.selectRowReviewCount(map_4);
			Map<String, Object> map_5 = new HashMap<String, Object>();
			map_5.put("off_num",off_num);
			map_5.put("rating",5);
			star_5 = offclassService.selectRowReviewCount(map_5);
			for(int i=0;i<list.size();i++) {
				OffstarVO offstarVO = list.get(i);
				System.out.println(offstarVO);
				offstarVO.setRating_percent(offstarVO.getRating()*20);
			}
		}
		
		
		mav.setViewName("offclassReviewList");
		mav.addObject("list", list);
		mav.addObject("count", count);
		mav.addObject("star_1", star_1);
		mav.addObject("star_2", star_2);
		mav.addObject("star_3", star_3);
		mav.addObject("star_4", star_4);
		mav.addObject("star_5", star_5);
		mav.addObject("rating", rating);
		//글 작성자 -강사
		mav.addObject("writer_num",writer_num);
		mav.addObject("photo_name",photo_name);
		mav.addObject("writer_name",writer_name);
		if(session_user_num!=null) {
			mav.addObject("session_user_num",session_user_num);
		}
		mav.addObject("off_num",off_num);
		
		return mav;
	}
	//오프라인 클래스 후기 수정폼
	@GetMapping("/offclass/offclassReviewUpdate.do")
	public String reviewformUpdate(@RequestParam int off_num,HttpSession session,Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("<<오프라인 클래스 후기 수정>>"+off_num);
		map.put("off_num", off_num);
		Integer user_num = (Integer)session.getAttribute("session_user_num");
		map.put("user_num", user_num);
		OffstarVO offstarVO = offclassService.selectOffReview(map);
		logger.info("<<오프라인 클래스 후기 수정offstarVO>>"+offstarVO);
		OffclassVO offclassVO = offclassService.selectOffClass(off_num);
		offstarVO.setOff_name(offclassVO.getOff_name());
		
		model.addAttribute("offstarVO",offstarVO);
		
		return "offclassReviewUpdate";
	}
	//오프라인 클래스 후기 수정
	@PostMapping("/offclass/offclassReviewUpdate.do")
	public String reviewsubmitUpdate(@Valid OffstarVO offstarVO, BindingResult result,Model model) {
		logger.info("<<수정 하기>>"+offstarVO);
		int off_num = offstarVO.getOff_num();
		if(result.hasErrors()) {
			return "offclassReviewUpdate";
		}
		
		offclassService.updateOffReview(offstarVO);
		
		model.addAttribute("offstarVO",offstarVO);
		
		return "redirect:/offclass/offclassReviewList.do?off_num="+off_num;
	}
}
