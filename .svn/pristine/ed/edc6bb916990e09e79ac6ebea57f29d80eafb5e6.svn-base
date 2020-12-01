package com.zqkj.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.mapping.MappedStatement;

import com.zqkj.utils.annotation.SqlFieldIgnore;

import cn.hutool.core.util.ClassUtil;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

public class SqlHelperUtil {
    /**
     * select xxx,xxx...
     *
     * @param entityClass
     * @return
     */
    public static String selectAllColumns(Class<?> entityClass, MappedStatement ms) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(entityClass, ms));
        sql.append(" ");
        return sql.toString();
    }
    /**
     * id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<?> entityClass, MappedStatement ms) {
    	Class<?> c = ClassUtil.loadClass(ms.getId().substring(0, ms.getId().lastIndexOf(".")));
    	SqlFieldIgnore sqlFieldIgnore = c.getAnnotation(SqlFieldIgnore.class);
    	Map<String, Boolean> fieldMap = new HashMap<String, Boolean>();
    	if(sqlFieldIgnore != null && sqlFieldIgnore.value() != null){
    		String value = sqlFieldIgnore.value();
    		String fields[] = value.split(",");
    		for (String string : fields) {
    			fieldMap.put(string.toLowerCase(), true);
			}
    	}
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnSet) {
        	if(fieldMap.get(entityColumn.getColumn().toLowerCase()) == null)
        		sql.append(entityColumn.getColumn()).append(",");
        }
        if(sqlFieldIgnore != null && sqlFieldIgnore.value() != null){
        	System.out.println(c + " ---- Ignore:" + fieldMap);
        	System.out.println(sql);
        }
        return sql.substring(0, sql.length() - 1);
    }
}
