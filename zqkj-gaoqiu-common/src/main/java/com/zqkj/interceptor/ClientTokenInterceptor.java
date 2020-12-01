package com.zqkj.interceptor;

import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.Content;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by ace on 2017/9/12.
 */
public class ClientTokenInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header("x-forwarded-for", BaseContentHandler.getIp());
		requestTemplate.header(Content.CONTEXT_KEY_USER_TOKEN, BaseContentHandler.getToken());
	}
}
