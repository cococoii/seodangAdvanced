package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.qna.dao.OffqnaMapper;
import kr.spring.qna.vo.OffqnaVO;

@Service
public class OffqnaServiceImpl implements OffqnaService{
	
	//의존 관계
	@Autowired
	private OffqnaMapper offqnaMapper;

	//글 목록
	@Override
	public List<OffqnaVO> getOffqnaList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return offqnaMapper.getOffqnaList(map);
	}

	//글 목록
	@Override
	public int selectOffqnaRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return offqnaMapper.selectOffqnaRowCount(map);
	}
	
	//글등록
	@Override
	public void insertOffqna(OffqnaVO Offqna) {
		// TODO Auto-generated method stub
		offqnaMapper.insertOffqna(Offqna);
	}

	@Override
	public OffqnaVO selectOffqna(Integer Offqna_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOffqnaHit(Integer Offqna_num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOffqna(OffqnaVO offqna) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOffqna(int num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOffqnaFile(Integer offqna_num) {
		// TODO Auto-generated method stub
		
	}
	
	
}
