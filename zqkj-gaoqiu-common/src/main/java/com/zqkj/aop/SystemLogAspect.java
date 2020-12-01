package com.zqkj.aop;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zqkj.bean.SystemLogEntity;
import com.zqkj.exception.ValidataException;
import com.zqkj.utils.BaseContentHandler;
import com.zqkj.utils.GsonUtil;
import com.zqkj.utils.annotation.SysLog;
import com.zqkj.utils.annotation.SysLogIgnore;

import io.swagger.annotations.Api;

//@Aspect
//@Component
public class SystemLogAspect {
	private Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

	@Pointcut("execution(* com.zqkj.controller..*.*(..))")
	public void pointCut() {
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint pjp) throws ValidataException, Throwable {
		Object returnObj = new Object();
		Date date = new Date();
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Object obj = pjp.getTarget();
		Method method = signature.getMethod();
		SysLog syslog = method.getAnnotation(SysLog.class);
		SysLogIgnore sysLogIgnore = pjp.getTarget().getClass().getAnnotation(SysLogIgnore.class);
		if (sysLogIgnore != null || syslog == null)
			return pjp.proceed();
		Api api = pjp.getTarget().getClass().getAnnotation(Api.class);
		String tabName = "";
		if (api != null && api.value() != null) {
			tabName = api.value();
		}
		// 请求的参数
		Object[] args = pjp.getArgs();
		Type[] types = method.getGenericParameterTypes();
		Class<?>[] clazzs = method.getParameterTypes();
		Class<?> tClazz = null;
		Type gs = obj.getClass().getGenericSuperclass();
		if (gs instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) gs;
			Type[] ts = pt.getActualTypeArguments();
			tClazz = (ts != null && ts.length == 2) ? (Class<?>) ts[1] : null;
		}
		if (types != null)
			for (int i = 0; i < types.length; i++) {
				if (types[i].getTypeName().equals("T"))
					clazzs[i] = tClazz;
				// else if(types[i].getTypeName().equals("T[]"))
				// clazzs[i] = (Array.newInstance(tClazz,3)).getClass();
				// System.out.println(types[i].getTypeName());
				// Array.newInstance(tClazz);
			}
		SystemLogEntity logEntity = new SystemLogEntity();
		logEntity.setOperation(tabName + ":" + syslog.value());
		logEntity.setMethod(method.getName());
		logEntity.setParams(GsonUtil.toJson(args));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logEntity.setCreateTime(sdf.format(date));
		logEntity.setOrganizationGuid(BaseContentHandler.getOrganizationGuid());
		logEntity.setUserGuid(BaseContentHandler.getUserGuid());
		logEntity.setVisitIP(BaseContentHandler.getIp());
		try {
			returnObj = pjp.proceed();
			logEntity.setContent(GsonUtil.toJson(returnObj));
		} catch (Throwable e) {
			logEntity.setContent(e.getMessage());
			throw e;
		} finally {
			logEntity.setRunTime(Integer.parseInt(String.valueOf(System.currentTimeMillis() - date.getTime())));
			//logFeignService.save(logEntity);
			log.info(GsonUtil.toJson(logEntity));
		}
		return returnObj;
	}
}
