package kr.spring.offclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.offclass.dao.OffclassMapper;
import kr.spring.offclass.vo.OffTimetableVO;
import kr.spring.offclass.vo.OffclassVO;
import kr.spring.offclass.vo.OfflikeVO;
import kr.spring.offclass.vo.OffstarReplyVO;
import kr.spring.offclass.vo.OffstarVO;
import kr.spring.offclass.vo.UploadFileOffVO;

@Service
@Transactional
public class OffclassServiceImpl implements OffclassService{
	
	@Autowired
	private OffclassMapper offclassMapper;

	@Override
	public List<OffclassVO> selectListOffClass(Map<String, Object> map) {
		return offclassMapper.selectListOffClass(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return offclassMapper.selectRowCount(map);
	}

	@Override
	public void insertOffClass(OffclassVO offclass,List<OffTimetableVO> list) {
		/* int num= offclassMapper.selectOff_num(); */
		offclass.setOff_num(offclass.getOff_num());
		offclassMapper.insertOffClass(offclass);
		/* System.out.println("-----------------------"+num); */
		for(int i=0;i<list.size();i++) {
			OffTimetableVO offTimetableVO=list.get(i);
			offTimetableVO.setOff_num(offclass.getOff_num());
			System.out.println("offTimetableVO"+list.get(i));
			offTimetableVO.setOff_limit(offclass.getOff_limit());
			System.out.println(offTimetableVO);
		}
		offclassMapper.insertListOffTime(list);
	}

	@Override
	public OffclassVO selectOffClass(Integer off_num) {
		return offclassMapper.selectOffClass(off_num);
	}

	@Override
	public List<OffTimetableVO> selectListOffTimetable(Map<String, Object> map) {
		return offclassMapper.selectListOffTimetable(map);
	}
	
	@Override
	public List<OffTimetableVO> selectListOffTimeDate(int off_num) {
		return offclassMapper.selectListOffTimeDate(off_num);
	}
	
	@Override
	public void updateOffClass(OffclassVO offclass,List<OffTimetableVO> list) {
		offclassMapper.updateOffClass(offclass);
		offclassMapper.deleteOffTimetable(offclass.getOff_num());
		for(int i=0;i<list.size();i++) {
			OffTimetableVO offTimetableVO=list.get(i);
			offTimetableVO.setOff_num(offclass.getOff_num());
			offTimetableVO.setOff_personcount(offclass.getOff_limit());
		}
		offclassMapper.insertListOffTime(list);
	}

	@Override
	public void deleteOffClass(Integer off_num) {
		offclassMapper.deleteOffClass(off_num);
	}

	//찜 기능
	@Override
	public void insertLike(Integer user_num, Integer off_num) {
		offclassMapper.insertLike(user_num, off_num);
	}

	@Override
	public void deleteLike(Integer offlike_num) {
		offclassMapper.deleteLike(offlike_num);
	}

	@Override
	public OfflikeVO selectLike(Integer user_num, Integer off_num) {
		return offclassMapper.selectLike(user_num, off_num);
	}

	@Override
	public int selectLikeCount(Integer off_num) {
		return offclassMapper.selectLikeCount(off_num);
	}

	@Override
	public void insertOffReview(OffstarVO offstarVO) {
		offclassMapper.insertOffReview(offstarVO);
	}

	@Override
	public List<OffstarVO> selectListOffReview(Map<String, Object> map) {
		return offclassMapper.selectListOffReview(map);
	}

	@Override
	public int selectRowReviewCount(Map<String, Object> map) {
		return offclassMapper.selectRowReviewCount(map);
	}
	
	@Override
	public OffstarVO selectOffReview(Map<String, Object> map) {
		return offclassMapper.selectOffReview(map);
	}

	@Override
	public void updateOffReview(OffstarVO offstarVO) {
		offclassMapper.updateOffReview(offstarVO);
	}

	@Override
	public void deleteOffReview(int offstar_num) {
		offclassMapper.deleteOffReviewReplyByOffstar(offstar_num);
		offclassMapper.deleteOffReview(offstar_num);
	}	
	@Override
	public float selectReviewRating(int off_num) {
		return offclassMapper.selectReviewRating(off_num);
	}

	@Override
	public void inserOffReviewReply(OffstarReplyVO offstarReplyVO) {
		offclassMapper.inserOffReviewReply(offstarReplyVO);
	}

	@Override
	public OffstarReplyVO selectOffReviewReply(int offstar_num) {
		return offclassMapper.selectOffReviewReply(offstar_num);
	}

	@Override
	public void updateOffReviewReply(OffstarReplyVO offstarReplyVO) {
		offclassMapper.updateOffReviewReply(offstarReplyVO);
	}

	@Override
	public void deleteOffReviewReply(int offre_num) {
		offclassMapper.deleteOffReviewReply(offre_num);
	}

	@Override
	public List<OffTimetableVO> selectTime(int time_num) {
		return offclassMapper.selectTime(time_num);
	}
	
	@Override
	public void fileUpload(String originalfileName, String saveFileName, long fileSize, int off_num) {
		// TODO Auto-generated method stub
		HashMap<String, Object> hm = new HashMap<String, Object>();
	    hm.put("originalfileName", originalfileName);
	    hm.put("saveFileName", saveFileName);
	    hm.put("fileSize", fileSize);
	    hm.put("off_num", off_num);

	    offclassMapper.uploadfileoff(hm);
	}
	@Override
	public List<UploadFileOffVO> selectFileOff(Integer off_num) {
		// TODO Auto-generated method stub
		return offclassMapper.selectFileOff(off_num);
	}

	@Override
	public void deleteFileOff(Integer off_num) {
		// TODO Auto-generated method stub
		offclassMapper.deleteFileOff(off_num);
	}

	@Override
	public int selectOff_num() {
		// TODO Auto-generated method stub
		return offclassMapper.selectOff_num();
	}

	

}
