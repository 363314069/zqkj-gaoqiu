package com.zqkj.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zqkj.exception.ValidataException;
import com.zqkj.utils.R;

import cn.hutool.core.util.ClassUtil;


/**
 * 系统日志，切面处理类
 * 
 */
@Aspect
@Component
public class ValidataAspect {
	public static Map<String, Object> map = new HashMap<String, Object>();
    @Pointcut("execution(* com.zqkj.controller..*.*(..))")
    public void pointCut(){}

	static {
    	Set<Class<?>> clazzs = ClassUtil.scanPackage("com.zqkj.controller.validata");
    	String key;
    	for (Class<?> clazz : clazzs) {
    		//if(clazz.isInterface() || clazz.getSimpleName())
    		if("BaseValidata".equals(clazz.getSimpleName()))
    			continue;
    		key = clazz.getSimpleName().replaceAll("Validata$", "Controller");
    		try {
				map.put(key, clazz.newInstance());
			} catch (Exception e) {
				System.err.println(clazz.getName());
				e.printStackTrace();
			}
		}
    }
	
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object obj = joinPoint.getTarget();
		Method method = signature.getMethod();
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		Object valiObj = map.get(obj.getClass().getSimpleName());
		try {
			invokeMethod(valiObj, obj, method, args);
		} catch (ValidataException e) {
			throw e;
		}
    }
    /*
    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }
    
    @AfterReturning(pointcut="pointCut()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
        System.out.println("AOP AfterReturning Advice:" + returnVal);
    }
    
    @AfterThrowing(pointcut="pointCut()",throwing="error")
    public void afterThrowing(JoinPoint joinPoint,Throwable error){
        System.out.println("AOP AfterThrowing Advice..." + error);
        System.out.println("AfterThrowing...");
    }
    
    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp){
        System.out.println("AOP Aronud before... a");
        try {
            pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("AOP Aronud after... a");
    }
    */
	public void invokeMethod(Object valiObj, Object obj, Method method, Object[] args) throws ValidataException{
		if(valiObj == null)
			throw new ValidataException(R.error(500, "No handler found for api " + obj.getClass().getSimpleName() + " " + method.getName()));
		Type[] types = method.getGenericParameterTypes();
		Class<?>[] clazzs = method.getParameterTypes();
		Class<?> tClazz = null;
		Type gs = obj.getClass().getGenericSuperclass();
		if(gs instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)gs;
			Type[] ts = pt.getActualTypeArguments();
			tClazz = (ts != null && ts.length == 2)?(Class<?>)ts[1]:null;
		}
		if(types != null){
			for (int i = 0; i < types.length; i++) {
				if(types[i].getTypeName().equals("T"))
					clazzs[i] = tClazz;
			}
		}
		Class<?> classInfo = valiObj.getClass();
		Method valiMethod = null;
		R r = null;
		try {
			valiMethod = classInfo.getMethod(method.getName(), clazzs);
			//method = classInfo.getDeclaredMethod(methodName, argsClass);
			r = (R) valiMethod.invoke(valiObj, args);
		} catch (Exception e) {
			System.err.println(e);
			throw new ValidataException(R.error(500, "No handler found for api" + obj.getClass().getSimpleName() + " " + method.getName()));
		}
		if(r != null && ((int)r.get(R.CODE_KEY)) != R.CODE_OK_VALUE){
			throw new ValidataException(r);
		}
	}
}
