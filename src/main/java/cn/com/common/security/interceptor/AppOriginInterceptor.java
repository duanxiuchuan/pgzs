package cn.com.common.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>TITLE： </p>
 * <p>DESCRIPTION： </p>
 *
 * @author: RM
 * @version 1.0
 * Created on 2018年11月16日
 * Copyright © 2018LiDaDa. All rights reserved.
 */
public class AppOriginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");  
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");  
		response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");  
		response.setHeader("X-Powered-By","Jetty");  
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
