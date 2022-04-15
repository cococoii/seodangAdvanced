package kr.spring.onclass.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import kr.spring.onclass.vo.OnclassVO;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.onclass.vo.OnstarReplyVO;
import kr.spring.onclass.vo.OnstarVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.qna.vo.OqnaReplyVO;
import kr.spring.user.vo.UserVO;



public interface OnclassService {
	public int selectOn_num();
	public int selectRowCount(Map<String,Object> map);
	public void insertOnclass(OnclassVO onclassVO);
	public int getOnclassCount();
	public List<OnclassVO> selectList(Map<String,Object> map);
	public List<OnclassVO> hitList(Map<String,Object> map);
	public OnclassVO getOnclass(int num);
	public void updateOnclass(OnclassVO onclassVO);
	public void deleteOnclass(Integer on_num);
	public OnclassVO selectOnclass(Integer on_num);
	public void updateHit(Integer on_num);
	//프로필 정보 출력
	public UserVO getProfile(int user_num);
	//찜 누럴ㅆ는지 확인
	public OnlikeVO selectLike(Integer user_num,Integer on_num);
	public void deleteLike(int onlike_num);
	public void insertLike(Integer user_num,Integer on_num);
	public int selectLikeCount(Integer on_num);

	//다중파일 업로드
	public void fileUpload(String originalfileName, String saveFileName, long fileSize, int on_num);
	//currval 미리 생성
	public int currSelect();
	//파일 가져오기
	public List<UploadFileVO> selectFile(int on_num);
	//파일 지우기
	public void deleteFile(int on_num);
	
	//실험
	/* public void fileMainUpdate(String mimage, int on_num); */
	
	//후기 작성
	//후기 작성
	public OnstarVO selectOnReview(Map<String, Object> map);
	public void insertOnReview(OnstarVO onstarVO);
	public int selectRowReviewCount(Map<String, Object> map);
	public float selectReviewRating(int on_num);
	public List<OnstarVO> selectListOnReview(Map<String, Object> map);
	public void updateOnReviewReply(OnstarReplyVO onstarReplyVO);
	public void updateOnReview(OnstarVO onstarVO);

}
