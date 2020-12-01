package com.zqkj.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zqkj.service.AuthorizedService;
import com.zqkj.service.ModuleService;
import com.zqkj.service.SecurityInterceptorService;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.jwt.JWTInfo;

@Service("securityInterceptorService")
public class SecurityInterceptorServiceImpl implements SecurityInterceptorService {
	
	@Autowired
	private AuthorizedService authorizedService;
	
	@Autowired
	private ModuleService moduleService;
	
	private static Map<String, Map<String, Boolean>> authorizedMap;
	private static Map<String, String> moduleMap;
	private static String defaultAuthGuid;
	private static String loginAuthGuid;
	private static String ipLoginAuthGuid;
	
	@PostConstruct
	public void initAuthorized(){
		authorizedMap = authorizedService.getModuleList();
		moduleMap = moduleService.getModuleMap(ModuleService.MODULE_TYPE_API);
		defaultAuthGuid = "605f5a01-084f-4615-b53f-8bbdd0013956";
		loginAuthGuid = "ccf6864a-2a18-4c6e-b5fe-01f4791431e5";
		ipLoginAuthGuid = "409ba5ad-6daa-4113-9e2f-9f90201b464e";
	}
	
	@Override
	public int SecurityValidate(String uri) {
		/*
		uri = uri.replaceAll("^", "");
		String moduleGuid = moduleMap.get(uri);
		Map<String, Boolean> authMap = authorizedMap.get(moduleGuid);
		if(moduleGuid == null || authMap == null){
			System.err.println("找不到路径：==》" + uri);
			return 402;
		}
		//是否为默认权限
		if(authMap.get(defaultAuthGuid) != null){
			return 0;
		}
		
		JWTInfo jwtInfo = BaseContentHandler.getJWTInfo();
		if(jwtInfo == null){
			return 401;
		}
		
		//通过角色受权
		List<String> roleGuids = jwtInfo.getListRole();
		if(roleGuids != null){
			for (String roleGuid : roleGuids) {
				if(authMap.get(roleGuid) != null){
					return 0;
				}
			}
		}
		
		//通过用户自身权限
		if(!StringUtils.isEmpty(jwtInfo.getGuid())){
			if(authMap.get(loginAuthGuid) != null){
				return 0;
			}
			
			if(authMap.get(jwtInfo.getGuid()) != null){
				return 0;
			} else {
				return 402;
			}
		} 
		//是否为默认权限

		if(authMap.get(ipLoginAuthGuid) != null){
			return 0;
		}

		return 401;*/
		return 0;
	}

}
