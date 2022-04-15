package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger =
			LoggerFactory.getLogger(AdminCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler)throws Exception{
		
		logger.info("<<AdminCheckInterceptor 진입>>");
		
		HttpSession session = request.getSession();
		Integer user_auth = (Integer)session.getAttribute("session_user_auth");
		if(user_auth == null || user_auth != 4) {
			//관리자 권한이 아닐 때
			RequestDispatcher dispatcher =
			request.getRequestDispatcher("/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		
		return true;
	}
}
 