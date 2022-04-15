package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.board.dao.BoardMapper;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

@Service //컨테이너에 포함되어야함
public class BoardServiceImpl implements BoardService{

	
	//(1) 의존관계 주입
	@Autowired
	private BoardMapper boardMapper;
	
	//(3-1) 글 목록
	@Override
	public List<BoardVO> selectList(Map<String, Object> map) {
		return boardMapper.selectList(map);
	}

	//(3-2) 글목록
	@Override
	public int selectRowCount(Map<String, Object> map) {
		return boardMapper.selectRowCount(map);
	}

	//(2) 글등록
	@Override
	public void insertBoard(BoardVO board) {
		boardMapper.insertBoard(board);		
	}

	//(4-1) 글상세
	@Override
	public BoardVO selectBoard(Integer board_num) {
		return boardMapper.selectBoard(board_num);
	}
	//(4-2) 글상세 - 조회수 증가
	@Override
	public void updateHit(Integer board_num) {
		boardMapper.updateHit(board_num);
	}

	//(6) 글수정
	@Override
	public void updateBoard(BoardVO board) {
		boardMapper.updateBoard(board);
	}

	//글 삭제
	@Override
	public void deleteBoard(Integer board_num) {
		boardMapper.deleteReplyByBoardNum(board_num);
		boardMapper.deleteBoard(board_num);
	}

	//(5) 파일삭제
	@Override
	public void deleteFile(Integer board_num) {
		boardMapper.deleteFile(board_num);		
	}

	/*======댓글======*/
	
	//2. 댓글 목록(1)
	@Override
	public List<BoardReplyVO> selectListReply(Map<String, Object> map) {
		return boardMapper.selectListReply(map);
	}

	//2. 댓글목록(2) - count
	@Override
	public int selectRowCountReply(Map<String, Object> map) {
		return boardMapper.selectRowCountReply(map);
	}

	//3-1. 댓글 수정 및 삭제 전 데이터 읽어오기 ?
	@Override
	public BoardReplyVO selectReply(Integer re_num) {
		return boardMapper.selectReply(re_num);
	}

	//1. 댓글등록
	@Override
	public void insertReply(BoardReplyVO boardReply) {
		boardMapper.insertReply(boardReply);
	}

	//3-2. 댓글 수정
	@Override
	public void updateReply(BoardReplyVO boardReply) {
		boardMapper.updateReply(boardReply);
	}

	//4. 댓글 삭제
	@Override
	public void deleteReply(Integer re_num) {
		boardMapper.deleteReply(re_num);
	}

}
