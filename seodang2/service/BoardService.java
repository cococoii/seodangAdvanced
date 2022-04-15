package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

public interface BoardService {

	//나중에 댓글작업을 할 때는 'deleteBoard'메서드를 트랜잭션처리해야한다.
	//나머지는 모두 트랜잭션처리를 하지 않는 개별작업용 메서드들임.
	
	//BoardMapper에서 전체 코드를 복붙해왔다.
	//부모글
	public List<BoardVO> selectList( Map<String,Object> map);
	public int selectRowCount( Map<String,Object> map);
	
	public void insertBoard(BoardVO board);
	
	public BoardVO selectBoard(Integer board_num);
	
	public void updateHit(Integer board_num);
	public void updateBoard(BoardVO board);
	
	public void deleteBoard(Integer board_num);
	public void deleteFile(Integer board_num);
	
	
	//댓글
	public List<BoardReplyVO> selectListReply(Map<String,Object> map);
	public int selectRowCountReply(Map<String,Object> map);
	public BoardReplyVO selectReply(Integer re_num);
	public void insertReply(BoardReplyVO boardReply);
	public void updateReply(BoardReplyVO boardReply);
	public void deleteReply(Integer re_num);
}
