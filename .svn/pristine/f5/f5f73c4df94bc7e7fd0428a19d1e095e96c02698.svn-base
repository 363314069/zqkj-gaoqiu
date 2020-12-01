package com.zqkj.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

public class ObjectUtil {

	/**
	 * 判断对象中属性值是否全为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean isAllNull(Object object) {
		if (null == object) {
			return true;
		}
		Method[] methods = object.getClass().getMethods(); // 获取所有公有方法
		for (Method m : methods) {
			if (m.getName().matches("get((?!Class).)*"))
				try {
					if (m.invoke(object) != null)
						return false;
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
		}
		return true;
	}
	
	/**
	 * 判断对象中ID属性值是否全为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean isIdEmpty(Object object) {
		if (null == object) {
			return true;
		}
		Method m;
		try {
			m = object.getClass().getMethod("getId");
			if (m.invoke(object) == null)
				return true;
		} catch (NoSuchMethodException e1) {
		} catch (SecurityException e1) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}
	
	/**
	 * 判断对象中Guid属性值是否全为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean isGuidEmpty(Object object) {
		if (null == object) {
			return true;
		}
		Method m;
		try {
			m = object.getClass().getMethod("getGuid");
			String val = (String) m.invoke(object);
			if (val == null || "".equals(val))
				return true;
		} catch (NoSuchMethodException e1) {
		} catch (SecurityException e1) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return false;
	}
	/**
	 * 判断List中所有对象属性值是否全为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean listObjIsNull(List<?> list) {
		if(list == null || list.size() == 0)
			return true;
		for (Object o : list) {
			if(isAllNull(o)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 *  判断map集合是否不为空
	 * @param map
	 * @return boolean true=not null ,false= null
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotMap(Map map){
		if (map != null && !map.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  判断map集合是否为空
	 * @param map
	 * @return boolean true= null ,false= not null
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isMap(Map map){
		if (map == null || map.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 *  判断集合是否为空
	 * @param collection
	 * @return boolean true=null ,false=not null
	 */
	public static boolean isCollection(Collection<?> collection){
		if (collection == null || collection.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  判断集合是否不为空
	 * @param collection
	 * @return boolean true=not null ,false=null
	 */
	public static boolean isNotCollection(Collection<?> collection){
		if (collection != null && !collection.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 *  判断数组是否为空
	 * @param array
	 * @return boolean  true=null ,false= not null
	 */
	public static <T> boolean isArray(T[] array){
		if (array == null || array.length == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  判断数组是否不为空
	 * @param array
	 * @return boolean true=not null ,false=null
	 */
	public static <T> boolean isNotArray(T[] array){
		if (array != null && array.length > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * 删除空字符串
	 * @param source
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 */
	 public static <T> T setNullValue(T source) throws IllegalArgumentException, IllegalAccessException, SecurityException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getGenericType().toString().equals("class java.lang.String")) {
                field.setAccessible(true); 
                Object obj = field.get(source);
                if (obj != null && obj.equals("")) {
                    field.set(source, null);
                } else if (obj != null) {
                    String str = obj.toString();
                    str = StringEscapeUtils.escapeSql(str);//StringEscapeUtils是commons-lang中的通用类
                    field.set(source, str.replace("\\", "\\" + "\\").replace("(", "\\(").replace(")", "\\)")
                            .replace("%", "\\%").replace("*", "\\*").replace("[", "\\[").replace("]", "\\]")
                            .replace("|", "\\|").replace(".", "\\.").replace("$", "\\$").replace("+", "\\+").trim()
                    );
                }
            }
        }
        return source;
    }
}
