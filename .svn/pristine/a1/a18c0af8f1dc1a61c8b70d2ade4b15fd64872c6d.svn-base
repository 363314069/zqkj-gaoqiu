package com.zqkj.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;
import com.zqkj.utils.JwtUtil;
import com.zqkj.utils.ip.IPUtil;
import com.zqkj.utils.jwt.JWTInfo;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by ace on 2017/9/10.
 */
@Component
public class HeaderInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,token");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "1800");
        if("OPTIONS".equals(request.getMethod())){
        	return false;
        }

        String token = request.getHeader(Content.CONTEXT_KEY_USER_TOKEN);
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
        }
        /*****************测试用户数据******************/
        /*
        BaseContentHandler.setIp(IPUtil.getIpAddr(request));
        BaseContentHandler.setLoginName("admin");
        BaseContentHandler.setUserName("管理员");
        BaseContentHandler.setUserGuid("d4053bff-dda5-11e8-95bd-0021ccc4eee8");
        BaseContentHandler.setUserId(1);
        BaseContentHandler.setOrganizationGuid("d4053bfa-dda5-11e8-95bd-0021ccc4eee8");
        BaseContentHandler.setOrganizationId(1);
        System.out.println(BaseContentHandler.getIp());*/
    	return super.preHandle(request, response, handler);
    }
    /*
    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
 
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }
	*/

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	//System.err.println(ex.toString());
    	super.afterCompletion(request, response, handler, ex);
    }
}
