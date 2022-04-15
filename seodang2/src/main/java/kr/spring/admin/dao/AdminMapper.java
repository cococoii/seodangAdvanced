package kr.spring.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;

import kr.spring.user.vo.UserVO;

public interface AdminMapper {

	//회원목록 (xml에 명시)
	public List<UserVO> selectList(Map<String,Object>map);
	public int selectRowCount(Map<String,Object>map);
	
	
	//회원정보 수정(auth)
	@Update("UPDATE ouser SET auth=#{auth} WHERE user_num=#{user_num}")
	public void updateMemberByAdmin(UserVO userVO);
	
	
	//회원정보 수정(auth 이외)
	//*관리자는 phone, zipcode, address, email만 바꿀 수 있도록 설정함
	@Update("UPDATE ouser_detail SET phone=#{phone}, zipcode=#{zipcode}, "
				+ "address1=#{address1}, address2=#{address2}, email=#{email} WHERE user_num=#{user_num}")
	public void updateMemberDetailByAdmin(UserVO userVO);
}
