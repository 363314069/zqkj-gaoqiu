package com.zqkj.configuration;

import com.zqkj.interceptor.HeaderInterceptor;
import com.zqkj.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ace on 2017/9/8.
 */
@Configuration("webConfig")
@Primary
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private SecurityInterceptor securityInterceptor;
	@Autowired
	private HeaderInterceptor headerInterceptor;

    @Value("${web.statics.htmlpath:#{'classpath:/statics/'}}")
	private String htmlpath;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		ArrayList<String> commonPathPatterns = getExcludeCommonPathPatterns();
        //registry.addInterceptor(getServiceAuthRestInterceptor()).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        //commonPathPatterns.add("/api/user/validate");
        registry.addInterceptor(headerInterceptor).addPathPatterns("/**");
        //registry.addInterceptor(securityInterceptor).addPathPatterns("/**");
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**").excludePathPatterns(commonPathPatterns.toArray(new String[]{}));
        //System.out.println("拦截器加载完成！");
        //super.addInterceptors(registry);
    }
 
    private ArrayList<String> getExcludeCommonPathPatterns() {
        ArrayList<String> list = new ArrayList<String>();
        String[] urls = {
                "/v2/api-docs",
                "/swagger-resources/**",
                //"/cache/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/security/oauth/**",
                "/security/swagger/**",
                "/jsp/**",
                "/ueditor/**",
                "/security/oauth/**"

        };
        /*String[] urls = {
        	"/security/oauth/**"
        };*/
        Collections.addAll(list, urls);
        return list;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(htmlpath);
        //registry.addResourceHandler("/**").addResourceLocations("file:D:/Workspaces/171207/ExamB2C/Html/zhongzhi/");
        System.err.println(htmlpath);
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/statics/");
    	//ApplicationHome h = new ApplicationHome(getClass());
        //File jarF = h.getSource();
        //System.out.println(jarF.getParentFile().toString());
    	//System.out.println(System.getProperty("user.dir"));
    	//registry.addResourceHandler("/**").addResourceLocations("file:" + jarF.getParentFile().toString() + "/statics/");
        //registry.addResourceHandler("/**").addResourceLocations("file:D:/Workspaces/171207/ExamB2C/Html/zhongzhi/");
    }
}
