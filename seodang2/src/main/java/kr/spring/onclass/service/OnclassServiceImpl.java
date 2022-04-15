package kr.spring.onclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.onclass.dao.OnclassMapper;
import kr.spring.onclass.vo.OnclassVO;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.onclass.vo.OnstarReplyVO;
import kr.spring.onclass.vo.OnstarVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.user.vo.UserVO;


@Service
@Transactional
public class OnclassServiceImpl implements OnclassService{
	
	@Autowired
	OnclassMapper onclassMapper;
	
	@Override
	public void insertOnclass(OnclassVO onclassVO) {
		// TODO Auto-generated method stub

		 //onclassVO.setOn_num(onclassMapper.selectOn_num());
		 onclassMapper.insertOnclass(onclassVO);

	}

	@Override
	public int getOnclassCount() {
		// TODO Auto-generated method stub
		return onclassMapper.getOnclassCount();
	}

	@Override
	public OnclassVO getOnclass(int num) {
		// TODO Auto-generated method stub
		return onclassMapper.getOnclass(num);
	}

	@Override
	public void updateOnclass(OnclassVO onclassVO) {
		// TODO Auto-generated method stub
		/* onclassMapper.deleteFile(onclassVO.getOn_num()); */
		onclassMapper.updateOnclass(onclassVO);
	}

	@Override
	public void deleteOnclass(Integer on_num) {
		// TODO Auto-generated method stub
		//게시물 지우기 전에 이미지 삭제
		onclassMapper.deleteFile(on_num);
		//게시물 지우기 전에 좋아요 삭제
		onclassMapper.deleteOnclassLike(on_num);
		onclassMapper.deleteOnclass(on_num);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.selectRowCount(map);
	}

	@Override
	public List<OnclassVO> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.selectList(map);
	}

	@Override
	public OnclassVO selectOnclass(Integer on_num) {
		// TODO Auto-generated method stub
		return onclassMapper.selectOnclass(on_num);
	}

	@Override
	public OnlikeVO selectLike(Integer user_num,Integer on_num) {
		// TODO Auto-generated method stub
		return onclassMapper.selectLike(user_num,on_num);
	}

	@Override
	public void deleteLike(int onlike_num) {
		// TODO Auto-generated method stub
		onclassMapper.deleteLike(onlike_num);
	}

	@Override
	public void insertLike(Integer user_num,Integer on_num) {
		// TODO Auto-generated method stub
		onclassMapper.insertLike(user_num,on_num);
	}

	@Override
	public void updateHit(Integer on_num) {
		// TODO Auto-generated method stub
		onclassMapper.updateHit(on_num);
	}

	@Override
	public List<OnclassVO> hitList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.hitList(map);
	}
	
	//프로필 정보 출력
	@Override
	public UserVO getProfile(int user_num) {
		// TODO Auto-generated method stub
		return onclassMapper.getProfile(user_num);
	}

	@Override
	public int selectLikeCount(Integer on_num) {
		// TODO Auto-generated method stub
		return onclassMapper.selectLikeCount(on_num);
	}

	@Override
	public int selectOn_num() {
		// TODO Auto-generated method stub
		return onclassMapper.selectOn_num();
	}

	
	//다중 파일 업로드
	@Override
	public void fileUpload(String originalfileName, String saveFileName, long fileSize,int on_num) {
	    HashMap<String, Object> hm = new HashMap<String, Object>();
	    hm.put("originalfileName", originalfileName);
	    hm.put("saveFileName", saveFileName);
	    hm.put("fileSize", fileSize);
	    hm.put("on_num", on_num);
	    
	    
	    onclassMapper.uploadFile(hm);
	}

	@Override
	public int currSelect() {
		// TODO Auto-generated method stub
		return onclassMapper.currSelect();
	}

	@Override
	public List<UploadFileVO> selectFile(int on_num) {
		// TODO Auto-generated method stub
		return onclassMapper.selectFile(on_num);
	}

	@Override
	public void deleteFile(int on_num) {
		// TODO Auto-generated method stub
		onclassMapper.deleteFile(on_num);
		
	}

	@Override
	public OnstarVO selectOnReview(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.selectOnReview(map);
	}

	@Override
	public void insertOnReview(OnstarVO onstarVO) {
		// TODO Auto-generated method stub
		onclassMapper.insertOnReview(onstarVO);
	}

	@Override
	public int selectRowReviewCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.selectRowReviewCount(map);
	}

	@Override
	public float selectReviewRating(int on_num) {
		// TODO Auto-generated method stub
		return onclassMapper.selectReviewRating(on_num);
	}

	@Override
	public List<OnstarVO> selectListOnReview(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onclassMapper.selectListOnReview(map);
	}

	@Override
	public void updateOnReviewReply(OnstarReplyVO onstarReplyVO) {
		// TODO Auto-generated method stub
		onclassMapper.updateOnReviewReply(onstarReplyVO);
	}

	@Override
	public void updateOnReview(OnstarVO onstarVO) {
		// TODO Auto-generated method stub
		onclassMapper.updateOnReview(onstarVO);
	}


}
