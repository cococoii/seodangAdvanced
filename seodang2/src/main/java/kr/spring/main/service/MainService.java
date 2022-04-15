package kr.spring.main.service;

import java.util.List;
import java.util.Map;

import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

public interface MainService {
	public int selectRowCount(Map<String,Object> map);
	public int getMainCount();
	public List<OnclassVO> selectList(Map<String,Object> map);
	public List<OffclassVO> selectListRank(Map<String,Object> map);
	public List<OffclassVO> selectListOffMain(Map<String,Object> map);
	public List<OnclassVO> selectListOnMain(Map<String,Object> map);
}
