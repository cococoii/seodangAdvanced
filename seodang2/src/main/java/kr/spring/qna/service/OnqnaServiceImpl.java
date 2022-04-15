package kr.spring.qna.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.qna.dao.OnqnaMapper;
import kr.spring.qna.vo.OnqnaVO;

@Service //컨테이너에 포함되어야함
public class OnqnaServiceImpl implements OnqnaService{

	
	//(1) 의존관계 주입
	@Autowired
	private OnqnaMapper onqnaMapper;
	
	//2. 글 목록
	@Override
	public List<OnqnaVO> getOnqnaList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onqnaMapper.getOnqnaList(map);
	}
	
	//2. 글 목록 - rowCount
	@Override
	public int selectOnqnaRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return onqnaMapper.selectOnqnaRowCount(map);
	}

	/*=============1.글등록==============*/
	@Override
	public void insertOnqna(OnqnaVO Onqna) {
		onqnaMapper.insertOnqna(Onqna);
	}
	
	/*=============3. 글상세==============*/
	@Override
	public OnqnaVO selectOnqna(Integer Onqna_num) {
		// TODO Auto-generated method stub
		return onqnaMapper.selectOnqna(Onqna_num);
	}
	/*=============조회수==============*/
	@Override
	public void updateOnqnaHit(Integer Onqna_num) {
		// TODO Auto-generated method stub
		
	}
	/*=============글수정(OnqnaMapper.xml에 명시)==============*/
	@Override
	public void updateOnqna(OnqnaVO Onqna) {
		// TODO Auto-generated method stub
		
	}
	/*=============글삭제==============*/
	@Override
	public void deleteOnqna(int num) {
		// TODO Auto-generated method stub
		
	}
	/*=============파일삭제==============*/
	@Override
	public void deleteOnqnaFile(Integer Onqna_num) {
		// TODO Auto-generated method stub
		
	}


}
