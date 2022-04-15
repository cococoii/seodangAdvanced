package kr.spring.myclass.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.myclass.dao.MyclassMapper;
import kr.spring.myclass.vo.MyclassVO;
import kr.spring.myclass.vo.PaymentVO;
import kr.spring.onclass.vo.OnclassVO;
import kr.spring.onclass.vo.OnlikeVO;

@Service
@Transactional
public class MyclassServiceImpl implements MyclassService{
	
	@Autowired
	MyclassMapper myclassMapper;
	
	@Override
	public int selectRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectRowCount(map);
	}

	@Override
	public List<MyclassVO> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectList(map);
	}

	@Override
	public void insertRegister(PaymentVO paymentVO) {
		// TODO Auto-generated method stub
		/* paymentVO.setOnreg_num(myclassMapper.selectSeq()); */
		System.out.println("시퀀스 : " + paymentVO.getOnreg_num());
		myclassMapper.insertRegister(paymentVO);
		System.out.println("시퀀스2 : " + paymentVO.getOn_num());
		myclassMapper.insertDetailRegister(paymentVO);
	}
	
	@Override
	public void insertRegisterOff(PaymentVO paymentVO) {
		// TODO Auto-generated method stub
		/* paymentVO.setOnreg_num(myclassMapper.selectSeq()); */
		System.out.println("시퀀스 : " + paymentVO.getOnreg_num());
		myclassMapper.insertRegister(paymentVO);
		System.out.println("시퀀스2 : " + paymentVO.getOff_num());
		myclassMapper.insertDetailRegisterOff(paymentVO);
	}

	@Override
	public int overlap(int on_num,int user_num) {
		// TODO Auto-generated method stub
		return myclassMapper.overlap(on_num,user_num);
	}

	@Override
	public List<PaymentVO> selectRegisterList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectRegisterList(map);
	}

	@Override
	public int selectRowCount2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectRowCount2(map);
	}

	@Override
	public void deletePayment(int onreg_num, int user_num) {
		// TODO Auto-generated method stub
		myclassMapper.deletePayment(onreg_num, user_num);
		myclassMapper.updateStatusPayment(onreg_num);
	}

	@Override
	public PaymentVO selectPayment(PaymentVO paymentVO) {
		// TODO Auto-generated method stub
		return myclassMapper.selectPayment(paymentVO);
	}

	@Override
	public List<OnlikeVO> selectLikeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectLikeList(map);
	}

	@Override
	public int selectRowCount3(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.selectRowCount3(map);
	}

	@Override
	public void onDeleteLike(int on_num) {
		// TODO Auto-generated method stub
		myclassMapper.onDeleteLike(on_num);
	}

	@Override
	public void offDeleteLike(int on_num) {
		// TODO Auto-generated method stub
		myclassMapper.offDeleteLike(on_num);
	}

	@Override
	public List<PaymentVO> buyerSelectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		System.out.println("맵 값"+map);
		
		return myclassMapper.buyerSelectList(map);
	}

	@Override
	public int buySelectRowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return myclassMapper.buySelectRowCount(map);
	}

	@Override
	public int peopleCount(int on_num) {
		// TODO Auto-generated method stub
		return myclassMapper.peopleCount(on_num);
	}

	@Override
	public void myclassDelete(int onreg_num, int user_num) {
		// TODO Auto-generated method stub
		myclassMapper.myclassDelete(onreg_num, user_num);
		myclassMapper.myclassDelete2(onreg_num);
	}

	@Override
	public void myclassUpdate(int onreg_num, int user_num) {
		// TODO Auto-generated method stub
		myclassMapper.myclassUpdate(onreg_num, user_num);
		myclassMapper.myclassUpdate2(onreg_num);
	}

	@Override
	public void myclassUserDelete(int onreg_num) {
		// TODO Auto-generated method stub
		myclassMapper.myclassUserDelete(onreg_num);
		myclassMapper.myclassUserDelete2(onreg_num);
	}

	@Override
	public int selectSeq() {
		// TODO Auto-generated method stub
		return myclassMapper.selectSeq();
	}

	@Override
	public int overlapoff(int off_num, int user_num) {
		// TODO Auto-generated method stub
		return myclassMapper.overlapoff(off_num, user_num);
	}



}
