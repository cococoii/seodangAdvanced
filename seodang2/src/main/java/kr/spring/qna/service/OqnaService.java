package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import kr.spring.qna.vo.OqnaReplyVO;
import kr.spring.qna.vo.OqnaVO;

public interface OqnaService {

	//[ 부모글 ] 
	
	//글 목록
	public List<OqnaVO> getOqnaList(Map<String,Object> map);
	
	//글 목록 - rowCount
	public int selectOqnaRowCount(Map<String,Object> map);
	
	/*=============1.글등록==============*/
	public void insertOqna(OqnaVO Oqna);
	
	/*=============글상세==============*/
	public OqnaVO selectOqna(Integer qna_num);
	
	
	/*=============글수정(OnqnaMapper.xml에 명시)==============*/
	public void updateOqna(OqnaVO Oqna);
	
	/*=============글삭제==============*/
	public void deleteOqna(int qna_num);
	
	/*=============파일삭제==============*/
	public void deleteOqnaFile(Integer qna_num);
	
	
	
	//[ 댓글 ] 
	
	//1. 댓글 등록
	public void insertReply(OqnaReplyVO oqnaReplyVO);
	
	//2-1. 댓글목록 count
	public int selectRowCountReply(Map<String,Object> map);
	//2-2. 댓글 목록 : xml에 명시
	public List<OqnaReplyVO> selectListReply(Map<String,Object> map);
	
	//3-1. 댓글 수정 및 삭제 전 기존댓글 전체 데이터 읽어오기 (Intercepter에서 사용)
	public OqnaReplyVO selectReply(Integer re_num);
	//3-2. 댓글 수정
	public void updateReply(OqnaReplyVO oqnaReplyVO);
	
	//4. 댓글 삭제
	public void deleteReply(Integer re_num);

	
}
