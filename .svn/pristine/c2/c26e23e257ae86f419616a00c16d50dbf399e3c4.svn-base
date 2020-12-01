package com.zqkj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zqkj.service.SecurityInterceptorService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.JwtUtil;
import com.zqkj.utils.ip.IPUtil;
import com.zqkj.utils.jwt.JWTInfo;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    //private Logger logger = LoggerFactory.getLogger(UserAuthRestInterceptor.class);
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private SecurityInterceptorService securityInterceptorService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	/*String token = request.getHeader(Content.CONTEXT_KEY_USER_TOKEN);
    	//System.err.println(token);
    	//System.err.println(request.getRequestURI());
    	BaseContentHandler.remove();
    	BaseContentHandler.setIp(IPUtil.getIpAddr(request));
    	if(!StringUtils.isBlank(token)){
			try {
				JWTInfo jwtInfo = jwtUtil.fromJwt(token, JWTInfo.class);
				BaseContentHandler.setLoginName(jwtInfo.getLoginName());
				BaseContentHandler.setUserName(jwtInfo.getUserName());
				BaseContentHandler.setUserId(jwtInfo.getId());
				BaseContentHandler.setUserGuid(jwtInfo.getGuid());
				BaseContentHandler.setOrganizationGuid(jwtInfo.getOrganizationGuid());
				BaseContentHandler.set("type", jwtInfo.getType());
				BaseContentHandler.set("jwtInfo", jwtInfo);
				BaseContentHandler.setToken(token);
				BaseContentHandler.setOpenid(jwtInfo.getOpenid());
				//System.out.println(token + "------" + token);
			} catch (ExpiredJwtException e) {
				// 超时异常不做处理
				e.printStackTrace();
			}catch (Exception e) {
				// 超时异常不做处理
				e.printStackTrace();
			}
    	}*/
    	/*
		if(BaseContentHandler.getUserId() == null){
			response.setContentType("application/json;charset=UTF-8");
			//response.addHeader("code", "401");
			//response.setStatus(401);
			//response.sendError(601 , GsonUtil.toJson(R.error("token验证失败,请重新登录!")));
			response.getWriter().println(GsonUtil.toJson(R.error(401, "token验证失败,请重新登录!")));
			return false;
			//throw new BaseException("token验证失败,请重新登录!", 401);
		}*/
    	int code = securityInterceptorService.SecurityValidate(request.getRequestURI());
    	switch (code) {
		case 401:
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(401);
			response.getWriter().println("您没有访问权限!");
			//response.getWriter().println(GsonUtil.toJson(R.error(401, "token验证失败,请重新登录!")));
			return false;
		case 402:
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(402);
			response.getWriter().println("您没有访问权限!");
			//response.getWriter().println(GsonUtil.toJson(R.error(402, "您没有访问权限!")));
			return false;
		}
    	return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
