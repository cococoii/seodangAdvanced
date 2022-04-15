package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import kr.spring.qna.vo.OnqnaVO;

public interface OnqnaService {

	//나중에 댓글작업을 할 때는 'deleteBoard'메서드를 트랜잭션처리해야한다.
	//나머지는 모두 트랜잭션처리를 하지 않는 개별작업용 메서드들임.

	//[ 부모글 ]
	
	//글 목록
	public List<OnqnaVO> getOnqnaList(Map<String,Object> map);
	//글 목록 - rowCount
	public int selectOnqnaRowCount(Map<String,Object> map);
	/*=============글등록==============*/
	public void insertOnqna(OnqnaVO Onqna);
	/*=============글상세==============*/
	public OnqnaVO selectOnqna(Integer Onqna_num);
	/*=============조회수==============*/
	public void updateOnqnaHit(Integer Onqna_num);
	/*=============글수정(OnqnaMapper.xml에 명시)==============*/
	public void updateOnqna(OnqnaVO Onqna);
	/*=============글삭제==============*/
	public void deleteOnqna(int num);
	/*=============파일삭제==============*/
	public void deleteOnqnaFile(Integer Onqna_num);
	
	
	
	//[ 댓글 ]
	
	
}
