package com.zqkj.dao;


import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.zqkj.utils.mapperhelper.BaseSelectProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface SelectDao<T> {
	  /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    T selectOne(T record);
    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    List<T> select(T record);
    
    /**
     * 查询全部结果
     *
     * @return
     */
	@SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    List<T> selectAll();
	/**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    int selectCount(T record);
	 /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param key
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    T selectByPrimaryKey(Object key);
    
    /**
     * 根据主键字段查询总数，方法参数必须包含完整的主键属性，查询条件使用等号
     * @param key
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    boolean existsWithPrimaryKey(Object key);
    
    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     * @param ids 如 "1,2,3,4"
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    List<T> selectByIds(String ids);
    
    /**
     * 查询全部结果
     * @return
     */
	@SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    List<T> selectLimit(T record);
}
