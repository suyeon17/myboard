package com.myboard.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.myboard.business.UserSession;

public class UserSessionFilter implements Filter {
	
	private static final String USER_SESSION = "userSession";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) arg0;
		
		HttpSession httpSession = httpRequest.getSession();
	    Object sessionObj = httpSession.getAttribute(USER_SESSION);
	    
	    if(sessionObj != null){
	    	UserSession userSession = (UserSession)sessionObj;
	    	
	    	httpSession.setAttribute(USER_SESSION, userSession);
	    }
		
	    arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
