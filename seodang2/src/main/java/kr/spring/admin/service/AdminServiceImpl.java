package kr.spring.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.admin.dao.AdminMapper;
import kr.spring.user.vo.UserVO;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

	//의존관계 주입
	@Autowired
	private AdminMapper adminMapper;
	
	
	//회원목록(1)
	@Override
	public List<UserVO> selectList(Map<String, Object> map) {
		return adminMapper.selectList(map);
	}
	//회원목록(1-2 : count)
	@Override
	public int selectRowCount(Map<String, Object> map) {
		return adminMapper.selectRowCount(map);
	}

	//회원정보 수정
	@Override
	public void updateMemberByAdmin(UserVO userVO) {
		adminMapper.updateMemberByAdmin(userVO);
		adminMapper.updateMemberDetailByAdmin(userVO);
	}
}
