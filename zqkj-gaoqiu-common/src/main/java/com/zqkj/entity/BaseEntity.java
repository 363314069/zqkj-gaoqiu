package com.zqkj.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.zqkj.utils.annotation.SqlFieldIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.IDynamicTableName;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

@Data
@EqualsAndHashCode(callSuper=false)
@SqlFieldIgnore("timetamp,extint,extint0,extint1,extstr,extstr0,extstr1")
public class BaseEntity implements IDynamicTableName, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	private Integer page;
	@Transient
	private Integer limit;
	@Transient
	private Long total;
	@Transient
	private String orderBy;
	@Transient
	private String dynamicTableName;
	@Transient
	private String dynamicSelectColumn;
	public void setDynamicIgnoreColumn(String column){
    	Map<String, Boolean> fieldMap = new HashMap<String, Boolean>();
    	if(!StringUtils.isAllBlank(column)){
    		String columns[] = column.split(",");
    		for (String string : columns) {
    			fieldMap.put(string.toLowerCase().trim(), true);
			}
    	}
        Set<EntityColumn> columnSet = EntityHelper.getColumns(this.getClass());
        StringBuilder sql = new StringBuilder();
        for (EntityColumn entityColumn : columnSet) {
        	if(fieldMap.get(entityColumn.getColumn().toLowerCase()) == null){
        		sql.append(entityColumn.getColumn()).append(",");
        	}
        }
		this.dynamicSelectColumn = sql.substring(0, sql.length() - 1);
	}
}
