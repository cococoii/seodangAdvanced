package kr.spring.onclass.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.offclass.vo.OffstarReplyVO;
import kr.spring.offclass.vo.OffstarVO;
import kr.spring.onclass.vo.OnclassVO;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.onclass.vo.OnstarReplyVO;
import kr.spring.onclass.vo.OnstarVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.qna.vo.OqnaReplyVO;
import kr.spring.user.vo.UserVO;

public interface OnclassMapper {
	public List<OnclassVO> selectList(Map<String,Object> map);
	public List<OnclassVO> hitList(Map<String, Object> map);
	public int selectRowCount(Map<String,Object> map);
	@Insert("insert into onclass (on_num, user_num,on_name,category_num,on_price,on_content,mimage) "
			+ "values(#{on_num},#{user_num},#{on_name},#{category_num},#{on_price},#{on_content},#{mimage})")
	public void insertOnclass(OnclassVO onclassVO);
	@Select("select * from onclass c join ouser o on c.user_num = o.user_num "
			+ "join ouser_detail d on o.user_num = d.user_num "
			+ "where on_num = #{on_num}")
	public OnclassVO selectOnclass(Integer on_num);
	@Select("select count(*) from onclass")
	public int getOnclassCount();
	public List<OnclassVO> getOnclassList(Map<String,Object> map);
	@Select("select * from onclass where user_num = #{num}")
	public OnclassVO getOnclass(int num);
	public void updateOnclass(OnclassVO onclassVO);
	@Delete("delete from onclass where on_num = #{num}")
	public void deleteOnclass(Integer on_num);
	//게시물 삭제하기 전 찜도 같이 삭제
	@Delete("delete from onlike where on_num = #{on_num}")
	public void deleteOnclassLike(Integer on_num);
	
	@Update("UPDATE onclass SET hit=hit+1 WHERE on_num=#{on_num}")
	public void updateHit(Integer on_num);
	//프로필정보 출력
	@Select("select * from ouser o join ouser_detail z on o.user_num = z.user_num where o.user_num = #{user_num}")
	public UserVO getProfile(int user_num);
	//인서트 온넘 생성
	@Select("select onclass_seq.nextval from dual")
	public int selectOn_num();
	
	
	/////////////////////////찜하기 시작////////////////////////////
	//찜 눌렀는지 확인
	@Select("select * from onlike where user_num = #{user_num} and on_num = #{on_num}")
	public OnlikeVO selectLike(@Param("user_num") Integer user_num,@Param("on_num") Integer on_num);
	//찜취소
	@Delete("delete from onlike where onlike_num = #{onlike_num}")
	public void deleteLike(int onlike_num);
	//찜 하기
	@Insert("insert into onlike (onlike_num,user_num,on_num,olike) values(onlike_seq.nextval,#{user_num},#{on_num},1)")
	public void insertLike(@Param("user_num") Integer user_num,@Param("on_num") Integer on_num);
	//찜한 갯수(모든 이용자)
	@Select("select count(*) from onlike where on_num = #{on_num}")
	public int selectLikeCount(Integer on_num);
	/////////////////////////찜하기 끝////////////////////////////		
	//파일 다중 업로드
	public void uploadFile(HashMap<String, Object> hm);
	//currval 미리 생성
	@Select("select onclass_seq.nextval from dual")
	public int currSelect();
	//파일 불러오기
	@Select("select * from uploadfile where on_num = #{on_num}")
	public List<UploadFileVO> selectFile(int on_num);
	//게시물 지울때 이미지파일도 같이 지우기 
	@Delete("delete from uploadfile where on_num = #{on_num}")
	public void deleteFile(int on_num);

	
	
	
	//후기 작성 
	@Select("SELECT * FROM onstar WHERE on_num=#{on_num} AND user_num=#{user_num}")
	public OnstarVO selectOnReview(Map<String, Object> map);
	//후기 작성하기
	@Insert("INSERT INTO onstar (onstar_num,user_num,on_num,rating,text) VALUES (onstar_seq.nextval,#{user_num},#{on_num},#{rating},#{text})")
	public void insertOnReview(OnstarVO onstarVO);
	public int selectRowReviewCount(Map<String, Object> map);
	@Select("SELECT ROUND(avg(rating),1) FROM onstar WHERE on_num=#{on_num} ORDER BY onstar_num DESC")
	public float selectReviewRating(int on_num);
	//후기 목록
	public List<OnstarVO> selectListOnReview(Map<String, Object> map);
	@Update("UPDATE onstar_reply SET onre_content=#{onre_content} WHERE onre_num=#{onre_num}")
	public void updateOnReviewReply(OnstarReplyVO onstarReplyVO);
	@Update("UPDATE onstar SET rating=#{rating},text=#{text} WHERE onstar_num=#{onstar_num}")
	public void updateOnReview(OnstarVO onstarVO);

}
