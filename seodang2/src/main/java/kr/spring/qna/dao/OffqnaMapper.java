package kr.spring.qna.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;

import kr.spring.qna.vo.OffqnaVO;

public interface OffqnaMapper {

	//부모글
	
	//글 목록
	public List<OffqnaVO> getOffqnaList(Map<String,Object> map);
	
	//글 목록 - rowCount
	public int selectOffqnaRowCount(Map<String,Object> map);
	
	/*=============1.글등록==============*/
	@Insert("INSERT INTO offqna(offqna_num, off_num, user_num, title, content, uploadfile, filename) "
			+ "VALUES(offqna_seq.nextval,#{off_num},#{user_num},#{title},#{content},#{uploadfile},#{filename})")
	public void insertOffqna(OffqnaVO Offqna);
	
	/*=============글상세==============*/
	public OffqnaVO selectOffqna(Integer Offqna_num);
	
	/*=============조회수==============*/
	public void updateOffqnaHit(Integer Offqna_num);
	
	/*=============글수정(OffqnaMapper.xml에 명시)==============*/
	public void updateOffqna(OffqnaVO Offqna);
	
	/*=============글삭제==============*/
	public void deleteOffqna(int num);
	
	/*=============파일삭제==============*/
	public void deleteOffqnaFile(Integer Offqna_num);
	
	
	


}
