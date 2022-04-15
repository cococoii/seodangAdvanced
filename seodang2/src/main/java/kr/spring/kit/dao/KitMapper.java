package kr.spring.kit.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.kit.vo.KitVO;
import kr.spring.kit.vo.UploadFileKitVO;
import kr.spring.onclass.vo.UploadFileVO;
import kr.spring.kit.vo.KitLikeVO;

public interface KitMapper {
	//키트목록
	public List<KitVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	//키트등록
	@Insert("INSERT INTO okit(kit_num,user_num,kit_name,kit_price,kit_quantity,kit_content,uploadfile,filename,kit_content2,mimage) "
			+ "VALUES (#{kit_num},#{user_num},#{kit_name},#{kit_price},#{kit_quantity},#{kit_content},#{uploadfile},#{filename},#{kit_content2},#{mimage})") 
	public void insertKit(KitVO kit);
	//키트detail
    @Select("SELECT * FROM okit k JOIN ouser u ON k.user_num = u.user_num WHERE k.kit_num=#{kit_num}")
	public KitVO selectKit(Integer kit_num);
	//키트 조회수
	@Update("UPDATE okit SET hit=hit+1 WHERE kit_num=#{kit_num}")
	public void updateHit(Integer kit_num);	
	//키트 수정
	public void updateKit(KitVO kit);
	//키트 삭제
	@Delete("DELETE FROM okit WHERE kit_num=#{kit_num}")
	public void deleteKit(Integer kit_num);
	//파일삭제
	@Update("UPDATE okit SET uploadfile='',filename='' WHERE kit_num=#{kit_num}")
	public void deleteFile(Integer kit_num);
	
	/////////////////////////찜하기 시작////////////////////////////
	//찜 눌렀는지 확인
	@Select("select * from okitlike where user_num = #{user_num} and kit_num = #{kit_num}")
	public KitLikeVO selectLike(@Param("user_num") Integer user_num,@Param("kit_num") Integer kit_num);
	//찜취소
	@Delete("delete from okitlike where kitlike_num = #{kitlike_num}")
	public void deleteLike(int kitlike_num);
	//찜 하기
	@Insert("insert into okitlike (kitlike_num,user_num,kit_num,olike) values(okitlike_seq.nextval,#{user_num},#{kit_num},1)")
	public void insertLike(@Param("user_num") Integer user_num,@Param("kit_num") Integer kit_num);
	//찜한 갯수(모든 이용자)
	@Select("select count(*) from okitlike where kit_num = #{kit_num}")
	public int selectLikeCount(Integer kit_num);
	/////////////////////////찜하기 끝////////////////////////////	
	
	
	/////////////////////////다중이미지 시작////////////////////////////
	//파일 다중 업로드
	public void uploadfilekit(HashMap<String, Object> hm);
	//currval 미리 생성
	@Select("select okit_seq.nextval from dual")
	public Integer currvalSelect();
	//파일 불러오기
	@Select("select * from uploadfilekit where kit_num = #{kit_num}")
	public List<UploadFileKitVO> selectFileKit(Integer kit_num);
	//게시물 지울때 이미지파일도 같이 지우기 
	@Delete("delete from uploadfilekit where kit_num = #{kit_num}")
	public void deleteFileKit(Integer kit_num);
	///////////////////////다중이미지 끝////////////////////////////
}