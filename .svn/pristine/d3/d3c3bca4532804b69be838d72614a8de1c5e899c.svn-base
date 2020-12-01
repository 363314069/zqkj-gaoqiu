package com.zqkj.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.zqkj.utils.PageUtil;

/**
 * 
 * @author yuliey
 *
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 保存一个实体，null属性也会保存
	 * 
	 * @param record
	 * @return
	 */
	public int insert(T record);

	/**
	 * 保存一个实体，null属性不会保存
	 * 
	 * @param record
	 * @return
	 */
	public int insertSelective(T record);

	/**
	 * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 */
	public int insertList(List<T> recordList);

	/**
	 * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
	 */
	public int insertUseGeneratedKeys(T record);
	
	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 */
	public int delete(T record);
	/**
	 * 根据id删除数据
	 */
	public int deleteById(Integer id);
	/**
	 * 根据id删除数据
	 */
	public int deleteByGuid(String guid);
	/**
	 * 根据Example条件删除数据，返回删除的条数
	 */
	public int deleteByExample(Object example);

	/**
	 * 根据Condition条件删除数据，返回删除的条数
	 */
	public int deleteByCondition(Object condition);
	
	/**
	 * 根据主键@Id进行删除，多个Id以逗号,分割
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByIds(String ids);

	/**
	 * 根据主键更新所有值
	 */
	public int updateByPrimaryKey(T record);
	
	/**
	 * 根据主键更新属性不为null的值
	 */
	public int updateByPrimaryKeySelective(T record);

	/**
	 * 根据Example条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	public int updateByExample(T record, Object example);

	/**
	 * 根据Example条件更新实体`record`包含的不是null的属性值，返回更新的条数
	 */
	public int updateByExampleSelective(T record, Object example);

	/**
	 * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	public int updateByCondition(T record, Object condition);

	/**
	 * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	public int updateByConditionSelective(T record, Object condition);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> select(T record, String orderBy);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> select(T record);
	
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	public PageUtil<T> select(PageUtil<T> pageUtil, T record);

	/**
	 * 查询全部结果，select(null)方法能达到同样的效果
	 */
	public List<T> selectAll();

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 */
	public T selectOne(T record);
	
	/**
	 * 根据id查询一条记录
	 */
	public T selectOneById(Integer id);
	
	/**
	 * 根据guid查询一条记录
	 */
	public T selectOneByGuid(String guid);
	
	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 */
	public int selectCount(T record);

	/**
	 * 根据主键@Id进行查询，多个Id以逗号,分割
	 * 
	 * @param id
	 * @return
	 */
	public List<T> selectByIds(String ids);

	/**
	 * 根据Condition条件进行查询
	 */
	public List<T> selectByCondition(Object condition);

	/**
	 * 根据Condition条件进行查询
	 */
	public int selectCountByCondition(Object condition);

	/**
	 * 根据Example条件进行查询
	 */
	public List<T> selectByExample(Object example);

	/**
	 * 根据Example条件进行查询，若有多条数据则抛出异常
	 */
	public T selectOneByExample(Object example);

	/**
	 * 根据Example条件进行查询总数
	 */
	public int selectCountByExample(Object example);
	
	/**
	 * 跟具ID数组批量删除数据
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Long[] ids);

	/**
	 * 跟具GUID数组批量删除数据
	 * @param guids
	 * @return
	 */
	public int deleteByGuids(String[] guids);
	
	/**
	 * 跟具ID数组查询数据
	 * @param ids
	 * @return
	 */
	public List<T> selectByIds(Long[] ids);

	/**
	 * 跟具GUID数组查询数据
	 * @param guids
	 * @return
	 */
	public List<T> selectByGuids(String[] guids);
	/**
	 * 获取继承子类的class
	 * @return
	 */
	public Class<?> getClazz();
	/**
	 * 获取类表名
	 * @return
	 */
	public String getTableName();
}
