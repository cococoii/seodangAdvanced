package kr.spring.main.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import kr.spring.offclass.vo.OffclassVO;
import kr.spring.onclass.vo.OnclassVO;

public interface MainMapper {
	public List<OnclassVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	@Select("select count(*) from onclass")
	public int getOnclassCount();
	//인기있는 온라인
	public List<OffclassVO> selectListRank(Map<String,Object> map);
	//오프라인 강의 리스트
	public List<OffclassVO> selectListOffMain(Map<String,Object> map);
	public List<OnclassVO> selectListOnMain(Map<String, Object> map);
}
