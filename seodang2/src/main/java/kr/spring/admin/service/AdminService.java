package kr.spring.admin.service;

import java.util.List;
import java.util.Map;

import kr.spring.user.vo.UserVO;

public interface AdminService {

	//회원목록
	public List<UserVO> selectList(Map<String,Object>map);
	public int selectRowCount(Map<String,Object>map);
	
	
	//회원정보 수정(auth)
	public void updateMemberByAdmin(UserVO userVO);
	
}
