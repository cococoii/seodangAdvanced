package kr.spring.qna.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.qna.vo.OnqnaVO;

public interface OnqnaMapper {

	//부모글
	
	//글 목록
	public List<OnqnaVO> getOnqnaList(Map<String,Object> map);
	
	//글 목록 - rowCount
	public int selectOnqnaRowCount(Map<String,Object> map);
	
	/*=============1.글등록==============*/
	@Insert("INSERT INTO onqna(onqna_num, on_num, user_num, title, content, uploadfile, filename) "
			+ "VALUES(onqna_seq.nextval,#{on_num},#{user_num},#{title},#{content},#{uploadfile},#{filename})")
	public void insertOnqna(OnqnaVO Onqna);
	
	/*=============글상세==============*/
	@Select("SELECT*FROM onqna q JOIN ouser u ON q.user_num=u.user_num JOIN ouser_detail d ON u.user_num=d.user_num "
			+ "WHERE q.onqna_num=#{onqna_num}")
	public OnqnaVO selectOnqna(Integer Onqna_num);
	
	/*=============조회수==============*/
	public void updateOnqnaHit(Integer Onqna_num);
	
	/*=============글수정(OnqnaMapper.xml에 명시)==============*/
	public void updateOnqna(OnqnaVO Onqna);
	
	/*=============글삭제==============*/
	public void deleteOnqna(int num);
	
	/*=============파일삭제==============*/
	public void deleteOnqnaFile(Integer Onqna_num);
	
	
	
	//댓글
	
}
