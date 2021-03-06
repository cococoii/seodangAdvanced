package kr.spring.user.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kr.spring.user.dao.UserMapper;
import kr.spring.user.vo.UserVO;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	


	@Override
	public void insertUser(UserVO user) {
		user.setUser_num(userMapper.selectUser_num());
		userMapper.insertUser(user);
		userMapper.insertUser_detail(user);
	}

	@Override
	public UserVO selectCheckUser(String id) {
		return userMapper.selectCheckUser(id);
	}

	@Override
	public UserVO selectUser(Integer user_num) {
		return userMapper.selectUser(user_num);
	}

	@Override
	public void updateUser(UserVO user) {
		userMapper.updateUser(user);
		
	}

	@Override
	public void updatePassword(UserVO user) {
		userMapper.updatePassword(user);
		
	}

	@Override
	public void deleteUser(Integer user_num) {
		userMapper.deleteUser(user_num);
		userMapper.deleteUser_detail(user_num);
	}


	@Override
	public void updateProfile(UserVO user) {
		userMapper.updateProfile(user);
	}

	@Override
	public List<UserVO> selectList(Map<String, Object> map) {
		return userMapper.selectList(map);
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return userMapper.selectRowCount(map);
	}

	@Override
	public void updateByAdmin(UserVO user) {
		userMapper.updateByAdmin(user);
		userMapper.updateDetailByAdmin(user);
	}

	@Override
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST ????????? ?????? ???????????? false??? setDoOutput??? true???
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST ????????? ????????? ???????????? ???????????? ???????????? ?????? ??????
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            
			sb.append("&client_id=4773f28302de6343d01ea2e1f9a61f53"); //????????? ???????????? key
			sb.append("&redirect_uri=http://localhost:8585/project/user/kakaoLogin"); // ????????? ????????? ??????
            
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
            
			// ?????? ????????? 200????????? ??????
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// ????????? ?????? ?????? JSON????????? Response ????????? ????????????
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
            
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);
            
			// Gson ?????????????????? ????????? ???????????? JSON?????? ?????? ??????
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
            
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
	}

	@Override
	public HashMap<String, Object> getUserInfo(String access_Token) {
		// ???????????? ????????????????????? ?????? ????????? ?????? ??? ????????? HashMap???????????? ??????
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// ????????? ????????? Header??? ????????? ??????
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").getAsString();

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}


}
