package com.zqkj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限(Token)验证
 * 
 */
@Component
public class ErrorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	/*
    	if(request.getDispatcherType().name().equals("ERROR")){
    		Gson gson = new Gson();
    		response.setContentType("application/json;charset=UTF-8"); 
    	    response.setCharacterEncoding("UTF-8"); 
    	    //response.setStatus(200);
    		PrintWriter out = response.getWriter(); 
            out.print(gson.toJson(R.error(404, "找不到资源" + request.getRequestURL()))); 
            return false;
    	}*/
        return true;
    }
    
    @Override
    public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
    	System.out.println("2---" + request.getRequestURL());
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        System.out.println("3---"+ex);
    }
}
