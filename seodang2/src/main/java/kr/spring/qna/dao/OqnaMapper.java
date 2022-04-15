package kr.spring.qna.dao;

import java.util.List; 
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import kr.spring.qna.vo.OqnaVO;
import kr.spring.qna.vo.OqnaReplyVO;

public interface OqnaMapper {

	//[ 부모글 ] 
	
	/*=============글목록==============*/
	public List<OqnaVO> getOqnaList(Map<String,Object> map);
	
	/*=============글 목록 - rowCount==============*/
	public int selectOqnaRowCount(Map<String,Object> map);
	
	/*=============글등록==============*/
	@Insert("INSERT INTO oqna(qna_num, user_num, title, content, uploadfile, filename) "
			+ "VALUES (oqna_seq.nextval,#{user_num},#{title},#{content},#{uploadfile},#{filename})")
	public void insertOqna(OqnaVO Oqna);
	
	/*=============글상세==============*/
	@Select("SELECT*FROM oqna q JOIN ouser u ON q.user_num=u.user_num JOIN ouser_detail d ON u.user_num=d.user_num "
			+ "WHERE q.qna_num=#{qna_num}")
	public OqnaVO selectOqna(Integer qna_num);
	
	
	/*=============글수정(OnqnaMapper.xml에 명시)==============*/
	public void updateOqna(OqnaVO Oqna);
	
	/*=============글삭제==============*/
	@Delete("DELETE FROM oqna WHERE qna_num=#{qna_num}")
	public void deleteOqna(int qna_num);
	
	/*=============파일삭제==============*/
	@Update("UPDATE oqna SET uploadfile='', filename='' WHERE qna_num=#{qna_num}")
	public void deleteOqnaFile(Integer qna_num);
	
	
	
	//[ 댓글 ] 
	
	//1. 댓글 등록
	@Insert("INSERT INTO oqna_reply (oqnare_num, content, qna_num, user_num) "
			+ "VALUES (oqna_reply_seq.nextval, #{content}, #{qna_num}, #{user_num})")
	public void insertReply(OqnaReplyVO oqnaReplyVO);
	
	//2-1. 댓글목록 count
	//*일단 sample과는 다르게 ouser랑 join을 안시켰는데, 나중에 에러나면 확인해보자.
	@Select("SELECT COUNT(*) FROM oqna_reply WHERE qna_num=#{qna_num}")
	public int selectRowCountReply(Map<String,Object> map);
	//2-2. 댓글 목록 : xml에 명시
	public List<OqnaReplyVO> selectListReply(Map<String,Object> map);
	
	//3-1. 댓글 수정 및 삭제 전 기존댓글 전체 데이터 읽어오기 (Intercepter에서 사용)
	@Select("SELECT*FROM oqna_reply WHERE oqnare_num = #{re_num}")
	public OqnaReplyVO selectReply(Integer re_num);
	//3-2. 댓글 수정
	@Select("UPDATE oqna_reply SET content=#{content}, re_mdate=SYSDATE"
			+ " WHERE oqnare_num=#{qnare_num}")
	public void updateReply(OqnaReplyVO oqnaReplyVO);
	
	//4. 댓글 삭제
	@Delete("DELETE FROM oqna_reply WHERE oqnare_num =#{re_num}")
	public void deleteReply(Integer re_num);
	//+ 부모글 삭제시 댓글이 존재하면 부모글 삭제전 댓글 삭제
	@Delete("DELETE FROM oqna_reply WHERE qna_num =#{qna_num}")
	public void deleteReplyByQnaNum(Integer qna_num);
}
