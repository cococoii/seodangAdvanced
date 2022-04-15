package kr.spring.myclass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.myclass.vo.MyclassVO;
import kr.spring.myclass.vo.PaymentVO;
import kr.spring.onclass.vo.OnlikeVO;

public interface MyclassMapper {
	//내가 올린 강의 (선생님)
	public List<MyclassVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	//구매한 과목 저장(학생)
	//구매목록 시퀀스 생성 
	@Select("select onreg_seq.nextval from dual")
	public int selectSeq();
	//onreg 저장
	@Insert("insert into onreg(onreg_num,user_num) values(#{onreg_num},#{user_num})")
	public void insertRegister(PaymentVO paymentVO);
	//onreg_detail 저장
	@Insert("insert into onreg_detail(onreg_num,on_num,on_payment,on_status) "
			+ "values(#{onreg_num},#{on_num},#{on_payment},#{on_status})")
	public void insertDetailRegister(PaymentVO paymentVO);
	//off클래스 detail저장
	@Insert("insert into onreg_detail(onreg_num,off_num,on_payment,on_status) "
			+ "values(#{onreg_num},#{off_num},#{on_payment},#{on_status})")
	public void insertDetailRegisterOff(PaymentVO paymentVO);
	
	//중복된 강의 확인
	@Select("select count(*) from onreg a join onreg_detail z on a.onreg_num = z.onreg_num "
			+ "where z.on_num = #{on_num} and a.user_num = #{user_num}")
	public int overlap(@Param("on_num") int on_num,@Param("user_num") int user_num);
	//오프라인클라스 중복 강의 확인
	@Select("select count(*) from onreg a join onreg_detail z on a.onreg_num = z.onreg_num "
			+ "where z.off_num = #{off_num} and a.user_num = #{user_num}")
	public int overlapoff(@Param("off_num") int off_num,@Param("user_num") int user_num);
	//내 강의 구매한 학생 목록
	public List<PaymentVO> buyerSelectList(Map<String,Object> map);
	//구매학생 목록 카운트
	public int buySelectRowCount(Map<String,Object> map);
	//수강인원 카운트
	@Select("select count(*) from onreg c join onreg_detail z on c.onreg_num = z.onreg_num "
			+ "where z.on_num = #{on_num}")
	public int peopleCount(int on_num);
	//수강중인 학생 수강취소
	@Update("update onreg set on_moregdate = SYSDATE where onreg_num=#{onreg_num} and user_num = #{user_num}")
	public void myclassDelete(@Param("onreg_num") int onreg_num, @Param("user_num") int user_num);
	@Update("update onreg_detail set on_status = '2' where onreg_num=#{onreg_num}")
	public void myclassDelete2(@Param("onreg_num") int onreg_num);
	//수강중인 학생 수강재시작
	@Update("update onreg set on_moregdate = SYSDATE where onreg_num=#{onreg_num} and user_num = #{user_num}")
	public void myclassUpdate(@Param("onreg_num") int onreg_num, @Param("user_num") int user_num);
	@Update("update onreg_detail set on_status = '1' where onreg_num=#{onreg_num}")
	public void myclassUpdate2(@Param("onreg_num") int onreg_num);
	//수강목록에 있는 학생 지우기
	@Delete("delete from onreg_detail where onreg_num = #{onreg_num}")
	public void myclassUserDelete(@Param("onreg_num") int onreg_num);
	@Delete("delete from onreg where onreg_num = #{onreg_num}")
	public void myclassUserDelete2(@Param("onreg_num") int onreg_num);
	
	
	//구매 목록
	public List<PaymentVO> selectRegisterList(Map<String,Object> map);
	public int selectRowCount2(Map<String,Object> map);
	//구매 취소
	@Update("update onreg set on_moregdate = SYSDATE where onreg_num=#{onreg_num} and user_num = #{user_num}")
	public void deletePayment(@Param("onreg_num") int onreg_num,@Param("user_num") int user_num);
	@Update("update onreg_detail set on_status = '2' where onreg_num=#{onreg_num}")
	public void updateStatusPayment(@Param("onreg_num") int onreg_num);
	//내 구매목록 조회
	@Select("select * from onreg a join onreg_detail b on a.onreg_num = b.onreg_num "
			+ "where a.user_num = #{user_num} and a.onreg_num = #{onreg_num}")
	public PaymentVO selectPayment(PaymentVO paymentVO);
	
	//찜목록 전체보기
	public List<OnlikeVO> selectLikeList(Map<String,Object> map);
	public int selectRowCount3(Map<String,Object> map);
	
	//찜 취소
	//온라인 찜
	@Delete("delete from onlike where on_num = #{on_num}")
	public void onDeleteLike(int on_num);
	@Delete("delete from offlike where off_num = #{on_num}")
	public void offDeleteLike(int on_num);
}
