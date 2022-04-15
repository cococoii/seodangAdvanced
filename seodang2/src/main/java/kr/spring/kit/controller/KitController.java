package kr.spring.kit.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.kit.service.KitService;
import kr.spring.kit.vo.KitVO;
import kr.spring.kit.vo.UploadFileKitVO;
import kr.spring.offclass.vo.UploadFileOffVO;
import kr.spring.kit.vo.KitLikeVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

@Controller
public class KitController {
	private static final Logger logger = LoggerFactory.getLogger(KitController.class);
	
	@Autowired
	private KitService kitService;
	
	//자바빈 초기화
	@ModelAttribute
	public KitVO initCommand() {
		return new KitVO();
	};
	//kit 등록
	@GetMapping("/kit/kitWrite.do")
	public String form() {
		int kit_num = kitService.currvalSelect();
		System.out.println(kit_num);
		return "kitWrite";
	}
	//글 등록 폼에서 전송된 데이터 처리
	@PostMapping("/kit/kitWrite.do")
		public String submit(@Valid KitVO kitVO,
				             BindingResult result,
				             HttpSession session,
				             HttpServletRequest request,
				             MultipartHttpServletRequest mhsq) throws IllegalStateException, IOException {
			logger.info("<<글 저장>> : " + kitVO);
			
			int kit_num = kitService.currvalSelect();
			System.out.println(kit_num);
			
			
			String uploadFolder = "/resources/image_upload";
			String realFolder = session.getServletContext().getRealPath(uploadFolder);
			
			System.out.println(realFolder);
			
	        File dir = new File(realFolder);
	        if (!dir.isDirectory()) {
	            dir.mkdirs();
	        }
	        
	        String mimage = null; 
	        //넘어온 파일을 리스트로 저장
	        List<MultipartFile> mf = mhsq.getFiles("uploadfilekit");
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
					System.out.println("4 : " + kit_num);
					
					kitService.fileUpload(originalfileName, saveFileName, fileSize, kit_num);
	            }
	        }
			
			
			
			//유효성 체크 결과 오류가 있으면 폼 호출
			if(result.hasErrors()) {
				return form();
			}
			
			Integer user_num = (Integer)session.getAttribute("session_user_num");
			logger.info("<<회원번호>> : " + user_num);
			//회원 번호 셋팅
			kitVO.setUser_num(user_num);
			System.out.println(mimage);
			
			kitVO.setKit_num(kit_num);
			kitVO.setMimage(mimage);
			//글쓰기
			kitService.insertKit(kitVO);
			
			return "redirect:/kit/kitList.do";
		}
	
	    //Kit 목록
		@RequestMapping("/kit/kitList.do")
		public ModelAndView process(
				@RequestParam(value="pageNum",defaultValue="1")
				int currentPage,
				@RequestParam(value="keyfield",defaultValue="")
				String keyfield,
				@RequestParam(value="keyword",defaultValue="")
				String keyword) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("keyfield",keyfield);
			map.put("keyword",keyword);
			
			int count = kitService.selectRowCount(map);
			
			PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,20,10,"kitList.do");
			
			map.put("start",page.getStartCount());
			map.put("end",page.getEndCount());
			
			List<KitVO> list = null;
			if(count > 0) {
				list = kitService.selectList(map);
			}
			ModelAndView mav = new ModelAndView();
			mav.setViewName("kitList");
			mav.addObject("count", count);
			mav.addObject("list",list);
			mav.addObject("pagingHtml",page.getPagingHtml());
			
			return mav;
		}
			
			//kit 상세
			@RequestMapping("/kit/kitDetail.do")
			public ModelAndView process(@RequestParam int kit_num) {
				logger.info("<<게시판 글 상세 - 글 번호>> : " + kit_num);

				List<UploadFileKitVO> uplist = kitService.selectFileKit(kit_num);
				ModelAndView mav = new ModelAndView();
				
				//해당 글의 조회수 증가
				kitService.updateHit(kit_num);
				
				KitVO kit = kitService.selectKit(kit_num);
				kit.setKit_name(StringUtil.useNoHtml(kit.getKit_name()));
				//다중 업로드
				mav.addObject("uplist",uplist);
				for(int i=0; i<uplist.size();i++) {
					mav.addObject("upfile"+i,uplist.get(i));
				}
				mav.setViewName("kitDetail");
				mav.addObject("size", uplist.size());
				mav.addObject("kit", kit);
				return mav;
			}
			//이미지 출력
			@RequestMapping("/kit/imageView.do")
			public ModelAndView viewImage(@RequestParam int kit_num) {
				KitVO kit = kitService.selectKit(kit_num);
				
				ModelAndView mav = new ModelAndView();
				mav.setViewName("imageView");
				mav.addObject("imageFile",kit.getUploadfile());
				mav.addObject("filename", kit.getFilename());
				return mav;
			}
			//파일 다운로드
			@RequestMapping("/kit/file.do")
			public ModelAndView download(@RequestParam int kit_num) {
				KitVO kit = kitService.selectKit(kit_num);
				
				ModelAndView mav = new ModelAndView();
				mav.setViewName("downloadView");
				mav.addObject("downloadFile", kit.getUploadfile());
				mav.addObject("filename", kit.getFilename());
				
				return mav;
			}
				
			//kit 수정
			@GetMapping("/kit/kitUpdate.do")
			public String formUpdate(@RequestParam int kit_num, Model model) {
				KitVO vo = kitService.selectKit(kit_num);
				model.addAttribute("kitVO",vo);
				return "kitUpdate";
			}
			//수정form 데이터 처리
			@PostMapping("/kit/kitUpdate.do")
			public String submitUpdate(@Valid KitVO kit,BindingResult result,
					                     HttpServletRequest request, Model model) {
				
				logger.info("<<kit 정보 수정>>" + kit);
				
				if(result.hasErrors()) {
					KitVO vo = kitService.selectKit(kit.getKit_num());
					kit.setFilename(vo.getFilename());
					
					return "kitUpdate";
				}
				
				kitService.updateKit(kit);
				
				model.addAttribute("message","글수정완료");
				model.addAttribute("url",request.getContextPath()+"/kit/kitList.do");
			
				return "common/resultView";
			}
			
			//글 삭제
			@RequestMapping("/kit/kitDelete.do")
			public String submitDelete(@RequestParam int kit_num) {
				kitService.deleteKit(kit_num);
				return "redirect:/kit/kitList.do";
			}
			
	}