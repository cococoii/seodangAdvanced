package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import kr.spring.qna.vo.OffqnaVO;

public interface OffqnaService {
	public List<OffqnaVO> getOffqnaList(Map<String,Object> map);
	//글 목록 - rowCount
	public int selectOffqnaRowCount(Map<String,Object> map);
	/*=============글등록==============*/
	public void insertOffqna(OffqnaVO Offqna);
	/*=============글상세==============*/
	public OffqnaVO selectOffqna(Integer Offqna_num);
	/*=============조회수==============*/
	public void updateOffqnaHit(Integer Offqna_num);
	/*=============글수정(offqnaMapper.xml에 명시)==============*/
	public void updateOffqna(OffqnaVO offqna);
	/*=============글삭제==============*/
	public void deleteOffqna(int num);
	/*=============파일삭제==============*/
	public void deleteOffqnaFile(Integer offqna_num);
	
	
}
