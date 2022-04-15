package kr.spring.myclass.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import kr.spring.myclass.vo.MyclassVO;
import kr.spring.myclass.vo.PaymentVO;
import kr.spring.onclass.vo.OnlikeVO;

public interface MyclassService {
	//내가 올린 강의(선생님)
	public int selectRowCount(Map<String,Object> map);
	public List<MyclassVO> selectList(Map<String,Object> map);
	//(온라인강의) 사용자가 구매한 강의
	public void insertRegister(PaymentVO paymentVO);
	//(오프라인강의) 사용자가 구매한 강의
	public void insertRegisterOff(PaymentVO paymentVO);
	//중복 구매 제한 
	public int overlap(int on_num,int user_num);
	//오프라인강의 중복 구매 제한
	public int overlapoff(int off_num,int user_num);
	//구매한 강의(학생)
	public List<PaymentVO> selectRegisterList(Map<String,Object> map);
	public int selectRowCount2(Map<String,Object> map);
	//구매 취소
	public void deletePayment(int onreg_num, int user_num);
	//내 구매 목록 정보 출력
	public PaymentVO selectPayment(PaymentVO paymentVO);
	
	//구매한 강의 학생 목록
	public List<PaymentVO> buyerSelectList(Map<String,Object> map);
	public int buySelectRowCount(Map<String,Object> map);
	//수강중인 학생 카운트
	public int peopleCount(int on_num);
	//수강중인 학생 수강취소
	public void myclassDelete(int onreg_num, int user_num);
	//수강중인 학생 수강재시작
	public void myclassUpdate(int onreg_num, int user_num);
	//수강목록에 있는 학생 지우기
	public void myclassUserDelete(int onreg_num);
	
	//찜 한 목록
	public List<OnlikeVO> selectLikeList(Map<String,Object> map);
	//찜 카운트
	public int selectRowCount3(Map<String,Object> map);
	//찜 온라인 취소
	public void onDeleteLike(int on_num);
	//찜 오프라인 취소
	public void offDeleteLike(int on_num);
	
	
	
	
	
	
	//////////////////
	public int selectSeq();
}
