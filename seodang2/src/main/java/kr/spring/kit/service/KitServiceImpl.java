package kr.spring.kit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.kit.dao.KitMapper;
import kr.spring.kit.vo.KitVO;
import kr.spring.kit.vo.UploadFileKitVO;
import kr.spring.onclass.vo.OnlikeVO;
import kr.spring.kit.vo.KitLikeVO;

@Service
public class KitServiceImpl implements KitService{

	@Autowired
	private KitMapper kitMapper;

	
	@Override
	public List<KitVO> selectList(Map<String, Object> map) {
		return  kitMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return kitMapper.selectRowCount(map);
	}

	@Override
	public void insertKit(KitVO kit) {
		kitMapper.insertKit(kit);
		
	}

	@Override
	public KitVO selectKit(Integer kit_num) {
		return kitMapper.selectKit(kit_num);
	}
	
	@Override
	public void updateHit(Integer kit_num) {
		kitMapper.updateHit(kit_num);
		
	}
	
	@Override
	public void updateKit(KitVO kit) {
		kitMapper.updateKit(kit);
		
	}

	@Override
	public void deleteKit(Integer kit_num) {
	    kitMapper.deleteKit(kit_num);
		
	}
	@Override
	public void deleteFile(Integer kit_num) {
		kitMapper.deleteFile(kit_num);
		
	}

	@Override
	public KitLikeVO selectLike(Integer user_num, Integer kit_num) {
		// TODO Auto-generated method stub
		return kitMapper.selectLike(user_num, kit_num);
	}

	@Override
	public void deleteLike(int kitlike_num) {
		// TODO Auto-generated method stub
		kitMapper.deleteLike(kitlike_num);
	}

	@Override
	public void insertLike(Integer user_num, Integer kit_num) {
		// TODO Auto-generated method stub
		kitMapper.insertLike(user_num, kit_num);
	}

	@Override
	public int selectLikeCount(Integer kit_num) {
		// TODO Auto-generated method stub
		return kitMapper.selectLikeCount(kit_num);
	}

	@Override
	public void fileUpload(String originalfileName, String saveFileName, long fileSize, int kit_num) {
		// TODO Auto-generated method stub
		HashMap<String, Object> hm = new HashMap<String, Object>();
	    hm.put("originalfileName", originalfileName);
	    hm.put("saveFileName", saveFileName);
	    hm.put("fileSize", fileSize);
	    hm.put("kit_num", kit_num);

	    kitMapper.uploadfilekit(hm);
	}

	@Override
	public Integer currvalSelect() {
		// TODO Auto-generated method stub
		return kitMapper.currvalSelect();
	}

	@Override
	public List<UploadFileKitVO> selectFileKit(Integer kit_num) {
		// TODO Auto-generated method stub
		return kitMapper.selectFileKit(kit_num);
	}

	@Override
	public void deleteFileKit(Integer kit_num) {
		// TODO Auto-generated method stub
		kitMapper.deleteFileKit(kit_num);
	}
}