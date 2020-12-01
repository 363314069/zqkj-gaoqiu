package com.zqkj.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zqkj.dao.BaseDao;
import com.zqkj.entity.BaseEntity;
import com.zqkj.service.BaseService;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.RedisUtil;
import com.zqkj.utils.StringUtil;
import com.zqkj.utils.xss.SQLFilter;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public class BaseServiceImpl<D extends BaseDao<T>, T extends BaseEntity> implements BaseService<T> {

	@Autowired
	protected D dao;
	@Autowired
	protected RedisUtil redisUtil;
	
	/**
	 * 保存一个实体，null属性也会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insert(T record) {
		return dao.insert(record);
	}

	/**
	 * 保存一个实体，null属性不会保存
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public int insertSelective(T record) {
		return dao.insertSelective(record);
	}

	/**
	 * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 */
	@Override
	public int insertList(List<T> recordList) {
		return dao.insertList(recordList);
	}

	/**
	 * 插入数据，限制为实体包含`id`属性并且必须为自增列，实体配置的主键策略无效
	 */
	@Override
	public int insertUseGeneratedKeys(T record) {
		return dao.insertUseGeneratedKeys(record);
	}

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 */
	@Override
	public int delete(T record) {
		return dao.delete(record);
	}

	/**
	 * 根据主键@Id进行删除
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteById(Integer id) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return this.deleteByExample(example);
	}

	/**
	 * 根据guid进行删除
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByGuid(String guid) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("guid", guid);
		return this.deleteByExample(example);
	}

	/**
	 * 根据主键@Id进行删除，多个Id以逗号,分割
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByIds(String ids) {
		return dao.deleteByIds(ids);
	}

	/**
	 * 根据Condition条件删除数据，返回删除的条数
	 */
	@Override
	public int deleteByCondition(Object condition) {
		return dao.deleteByCondition(condition);
	}

	/**
	 * 根据Example条件删除数据，返回删除的条数
	 */
	@Override
	public int deleteByExample(Object example) {
		return dao.deleteByExample(example);
	}

	/**
	 * 根据主键更新所有值
	 */
	@Override
	public int updateByPrimaryKey(T record) {
		return dao.updateByPrimaryKey(record);
	}

	/**
	 * 根据主键更新属性不为null的值
	 */
	@Override
	public int updateByPrimaryKeySelective(T record) {
		return dao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	@Override
	public int updateByCondition(T record, Object condition) {
		return dao.updateByCondition(record, condition);
	}

	/**
	 * 根据Condition条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	@Override
	public int updateByConditionSelective(T record, Object condition) {
		return dao.updateByConditionSelective(record, condition);
	}

	/**
	 * 根据Example条件更新实体`record`包含的全部属性，null值会被更新，返回更新的条数
	 */
	@Override
	public int updateByExample(T record, Object example) {
		return dao.updateByExample(record, example);
	}

	/**
	 * 根据Example条件更新实体`record`包含的不是null的属性值，返回更新的条数
	 */
	@Override
	public int updateByExampleSelective(T record, Object example) {
		return dao.updateByExampleSelective(record, example);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	@Override
	public List<T> select(T record, String orderBy) {
		//PageHelper.startPage(1, 1000, false);
		if(!StringUtil.isEmpty(orderBy)){
			PageHelper.orderBy(SQLFilter.sqlInject(orderBy));
		}
		return dao.select(record);
	}
	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 */
	@Override
	public List<T> select(T record) {
		return this.select(record, null);
	}
	/**
	 * 根据实体中的属性值进行分页查询，查询条件使用等号
	 */
	@Override
	public PageUtil<T> select(PageUtil<T> pageUtil, T record) {
		
		Page<T> page = PageHelper.startPage(pageUtil.getPage(), pageUtil.getLimit(), pageUtil.getCount() == 0);
		if(!StringUtil.isEmpty(pageUtil.getOrderBy())){
			page.setOrderBy(pageUtil.getOrderBy());
		}
		dao.select(record);
		pageUtil = new PageUtil<T>(page, page.getTotal(), page.getPageSize(), page.getPageNum());
		return pageUtil;
	}

	/**
	 * 查询全部结果，select(null)方法能达到同样的效果
	 */
	@Override
	public List<T> selectAll() {
		return dao.selectAll();
	}

	/**
	 * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 */
	@Override
	public T selectOne(T record) {
		return dao.selectOne(record);
	}
	
	/**
	 * 根据id查询一条记录
	 */
	@Override
	public T selectOneById(Integer id) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", id);
		return this.selectOneByExample(example);
	}
	
	/**
	 * 根据guid查询一条记录
	 */
	@Override
	public T selectOneByGuid(String guid) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("guid", guid);
		return this.selectOneByExample(example);
	}

	/**
	 * 根据实体中的属性查询总数，查询条件使用等号
	 */
	@Override
	public int selectCount(T record) {
		return dao.selectCount(record);
	}

	/**
	 * 根据主键@Id进行查询，多个Id以逗号,分割
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<T> selectByIds(String ids) {
		return dao.selectByIds(ids);
	}

	/**
	 * 根据Condition条件进行查询
	 */
	@Override
	public List<T> selectByCondition(Object condition) {
		return dao.selectByCondition(condition);
	}

	/**
	 * 根据Condition条件进行查询
	 */
	@Override
	public int selectCountByCondition(Object condition) {
		return dao.selectCountByCondition(condition);
	}

	/**
	 * 根据Example条件进行查询
	 */
	@Override
	public List<T> selectByExample(Object example) {
		return dao.selectByExample(example);
	}

	/**
	 * 根据Example条件进行查询，若有多条数据则抛出异常
	 */
	@Override
	public T selectOneByExample(Object example) {
		return dao.selectOneByExample(example);
	}

	/**
	 * 根据Example条件进行查询总数
	 */
	@Override
	public int selectCountByExample(Object example) {
		return dao.selectCountByExample(example);
	}

	/**
	 * 跟具ID数组批量删除数据
	 * @param ids
	 * @return
	 */
	@Override
	public int deleteByIds(Long[] ids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<Long> idList = new ArrayList<Long>(Arrays.asList(ids));
		criteria.andIn("id", idList);
		return dao.deleteByExample(example);
	}

	/**
	 * 跟具GUID数组批量删除数据
	 * @param guids
	 * @return
	 */
	@Override
	public int deleteByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		return dao.deleteByExample(example);
	}

	/**
	 * 跟具ID数组查询数据
	 * @param ids
	 * @return
	 */
	@Override
	public List<T> selectByIds(Long[] ids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<Long> idList = new ArrayList<Long>(Arrays.asList(ids));
		criteria.andIn("id", idList);
		return dao.selectByExample(example);
	}

	/**
	 * 跟具GUID数组查询数据
	 * @param guids
	 * @return
	 */
	@Override
	public List<T> selectByGuids(String[] guids) {
		Example example = new Example(getClazz());
		Criteria criteria = example.createCriteria();
		List<String> guidList = new ArrayList<String>(Arrays.asList(guids));
		criteria.andIn("guid", guidList);
		return dao.selectByExample(example);
	}
	
	/**
	 * 获取继承子类的class
	 * @return
	 */
	@Override
	public Class<?> getClazz() {
		Class<?> clazz = this.getClass();
		// getSuperclass()获得该类的父类
		// System.out.println(clazz.getSuperclass());
		// getGenericSuperclass()获得带有泛型的父类
		// Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
		Type type = clazz.getGenericSuperclass();
		// System.out.println(type);
		// ParameterizedType参数化类型，即泛型
		ParameterizedType p = (ParameterizedType) type;
		// getActualTypeArguments获取参数化类型的数组，泛型可能有多个
		return (Class<?>) p.getActualTypeArguments()[1];
	}

	@Override
	public String getTableName() {
		Class<?> entityClass = getClazz();
		Table table = entityClass.getAnnotation(Table.class);
		return table.name();
	}

}
