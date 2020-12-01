package com.zqkj.utils.mapperhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zqkj.utils.annotation.SqlFieldIgnore;

import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.IDynamicTableName;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

public class SqlHelper extends tk.mybatis.mapper.mapperhelper.SqlHelper {
    
    /**
     * select xxx,xxx...
     *
     * @param entityClass
     * @return
     */
    public static String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            sql.append("<choose>");
            sql.append("<when test=\"dynamicSelectColumn != null and dynamicSelectColumn != ''\">");
            sql.append("${dynamicSelectColumn}");
            sql.append("</when>");
            //不支持指定列的时候查询全部列
            sql.append("<otherwise>");
            sql.append(getAllColumns(entityClass));
            sql.append("</otherwise>");
            sql.append("</choose>");
            return sql.toString();
        } else {
        	sql.append(getAllColumns(entityClass));
        }
        sql.append(" ");
        return sql.toString();
    }
    /**
     * select xxx,xxx...
     *
     * @param entityClass
     * @return
     */
    public static String selectListAllColumns(Class<?> entityClass) {
    	 StringBuilder sql = new StringBuilder();
         sql.append("SELECT ");
         if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
             sql.append("<choose>");
             sql.append("<when test=\"dynamicSelectColumn != null and dynamicSelectColumn != ''\">");
             sql.append("${dynamicSelectColumn}");
             sql.append("</when>");
             //不支持指定列的时候查询全部列
             sql.append("<otherwise>");
             sql.append(getListAllColumns(entityClass));
             sql.append("</otherwise>");
             sql.append("</choose>");
             return sql.toString();
         } else {
         	sql.append(getListAllColumns(entityClass));
         }
         sql.append(" ");
         return sql.toString();
    }
    /**
     * id,name,code...
     *
     * @param entityClass
     * @return
     */
    public static String getListAllColumns(Class<?> entityClass) {
    	SqlFieldIgnore sqlFieldIgnore = entityClass.getAnnotation(SqlFieldIgnore.class);
    	Map<String, Boolean> fieldMap = new HashMap<String, Boolean>();
    	if(sqlFieldIgnore != null && sqlFieldIgnore.value() != null){
    		String value = sqlFieldIgnore.value();
    		String fields[] = value.split(",");
    		for (String string : fields) {
    			fieldMap.put(string.trim().toLowerCase(), true);
			}
    	}
    	Class<?> superEntityClass = entityClass.getSuperclass();
    	sqlFieldIgnore = superEntityClass.getAnnotation(SqlFieldIgnore.class);
    	if(sqlFieldIgnore != null && sqlFieldIgnore.value() != null){
    		String value = sqlFieldIgnore.value();
    		String fields[] = value.split(",");
    		for (String string : fields) {
    			fieldMap.put(string.trim().toLowerCase(), true);
			}
    	}
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnSet) {
        	if(fieldMap.get(entityColumn.getColumn().toLowerCase()) == null){
        		sql.append(entityColumn.getColumn()).append(",");
        	}
        }
        return sql.substring(0, sql.length() - 1);
    }
    
    /**
     * where鎵�鏈夊垪鐨勬潯浠讹紝浼氬垽鏂槸鍚�!=null
     *
     * @param entityClass
     * @param empty
     * @param useVersion
     * @return
     */
    public static String whereAllIfColumnsByLimit(Class<?> entityClass, String select, String id, boolean empty) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = false;
        boolean useVersion = false;
        sql.append("<where>");
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        EntityColumn logicDeleteColumn = SqlHelper.getLogicDeleteColumn(entityClass);
        for (EntityColumn column : columnSet) {
            if (!useVersion || !column.getEntityField().isAnnotationPresent(Version.class)) {
                if (logicDeleteColumn != null && logicDeleteColumn == column) {
                    hasLogicDelete = true;
                    continue;
                }
                sql.append(getIfNotNull(column, " AND " + column.getColumnEqualsHolder(), empty));
            }
        }
        if (useVersion) {
            sql.append(whereVersion(entityClass));
        }
        if (hasLogicDelete) {
            sql.append(whereLogicDelete(entityClass, false));
        }
        sql.append("<if test=\"pageUtil.page &gt; 1\">");
        sql.append(" AND " + id + " not in(" + select + ")");
        sql.append("</if>");
        sql.append("</where>");
        return sql.toString();
    }
    
    public static String selectListAllColumnsByLimit(Class<?> entityClass) {
   	 StringBuilder sql = new StringBuilder();
        sql.append("SELECT top ${pageUtil.limit} ");
        if (IDynamicTableName.class.isAssignableFrom(entityClass)) {
            sql.append("<choose>");
            sql.append("<when test=\"dynamicSelectColumn != null and dynamicSelectColumn != ''\">");
            sql.append("${dynamicSelectColumn}");
            sql.append("</when>");
            //不支持指定列的时候查询全部列
            sql.append("<otherwise>");
            sql.append(getListAllColumns(entityClass));
            sql.append("</otherwise>");
            sql.append("</choose>");
            return sql.toString();
        } else {
        	sql.append(getListAllColumns(entityClass));
        }
        sql.append(" ");
        return sql.toString();
   }
}
