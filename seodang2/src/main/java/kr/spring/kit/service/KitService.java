package kr.spring.kit.service;

import java.util.List;
import java.util.Map;

import kr.spring.kit.vo.KitVO;
import kr.spring.kit.vo.UploadFileKitVO;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.kit.vo.KitLikeVO;

public interface KitService {
	
	public List<KitVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String, Object> map);
	public void insertKit(KitVO kit);
	public KitVO selectKit(Integer kit_num);
	public void updateHit(Integer kit_num);	
	public void updateKit(KitVO kit);
	public void deleteKit(Integer kit_num);
	public void deleteFile(Integer kit_num);
	
	//좋아요
	/*
	 * public KitLikeVO selectKitLike(Integer user_num,Integer kit_num); public void
	 * insertKitLike(Integer user_num, Integer kit_num); public void
	 * deleteKitLike(Integer kitLike_num); public int selectLikeCount(Integer
	 * kit_num);
	 */
	
	//임ㅁ시좋아요
	public KitLikeVO selectLike(Integer user_num,Integer kit_num);
	public void deleteLike(int kitlike_num);
	public void insertLike(Integer user_num,Integer kit_num);
	public int selectLikeCount(Integer kit_num);
	
	//다중파일 업로드
	public void fileUpload(String originalfileName, String saveFileName, long fileSize, int kit_num);
	//currval 미리 생성
	public Integer currvalSelect();
	//파일 가져오기
	public List<UploadFileKitVO> selectFileKit(Integer kit_num);
	//파일 지우기
	public void deleteFileKit(Integer kit_num);
}