package com.zqkj.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.StringUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;

/**
 * 系统日志，切面处理类
 * 
 */
@Aspect
@Component
public class DaoAspect {
	public static Map<String, Method> map = new HashMap<String, Method>();
	public static final String METHODS = ",guid,creator,createDate,modifier,modifiedDate,organizationGuid,createUser,createTime,id,".toLowerCase();
	
    static {
    	Set<Class<?>> clazzs = ClassUtil.scanPackage("com.zqkj.entity");
    	String key;
    	for (Class<?> clazz : clazzs) {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String methodname = method.getName().toLowerCase();
				if(methodname.matches("^(get|set).*$") && METHODS.indexOf(methodname.replaceAll("^(get|set)", ",") + ",") != -1){
					key = clazz.getSimpleName() + "." + methodname;
					map.put(key, method);
				}
			}
		}
    }
    
	@Pointcut("execution(* com.zqkj.dao.mapper..*.insert*(..))")
	public void insertPointCut() {
	}
	
	@Pointcut("execution(* com.zqkj.dao.mapper..*.update*(..))")
	public void updatePointCut() {
	}
	
	@Before("insertPointCut()")
	public void insert(JoinPoint pjp) throws Throwable {
		Object[] obj = pjp.getArgs();
		for (Object entity : obj) {
			insertInvoke(entity);
		}
		//System.err.println("insert" + GsonUtil.toJson(obj));
	}
	
	public void insertInvoke(Object entity) {
		if(entity == null)
			return;
		if(entity instanceof List){
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) entity;
			for (Object obj : list) {
				insertInvoke(obj);
			}
		} else {
			invoke(entity, "guid", StringUtil.generateGUID());
			invoke(entity, "creator", BaseContentHandler.getUserGuid());
			invoke(entity, "createDate", DateUtil.now());
			invoke(entity, "createUser", BaseContentHandler.getUserGuid());
			//invoke(entity, "modifier", BaseContentHandler.getLoginName());
			//invoke(entity, "modifiedDate", DateUtil.now());
			invoke(entity, "createTime", DateUtil.now());
			invoke(entity, "organizationGuid", BaseContentHandler.getOrganizationGuid());
		}
	}
	
	@Before("updatePointCut()")
	public void update(JoinPoint pjp) throws Throwable {
		Object[] obj = pjp.getArgs();
		for (Object entity : obj) {
			updateInvoke(entity);
		}
	}
	
	public void updateInvoke(Object entity) {
		if(entity == null)
			return;
		if(entity instanceof List){
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) entity;
			for (Object obj : list) {
				insertInvoke(obj);
			}
		} else {
			invoke(entity, "modifiedDate", DateUtil.now());
		}
	}
	public Method getMethod(Class<?> clazz, String methodName){
		Method method = map.get(clazz.getSimpleName() + "." + methodName.toLowerCase());
		return method;
	}
	
	public void invoke(Object entity, String field, Object value){
		Class<?> clazz = entity.getClass();
		try {
			Method getGuid = getMethod(clazz, "get" + field);
			if(null != getGuid){
				Object obj = getGuid.invoke(entity);
				if (obj == null || "".equals(obj)) {
					Method setGuid = getMethod(clazz, "set" + field);
					if (null != setGuid) {
						setGuid.invoke(entity, value);
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
