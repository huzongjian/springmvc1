package com.springmvc.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.springmvc.base.dao.BaseDao;

/**
 * DAO的Hibernate基类
 * 
 * @author pasu
 * @param <T>
 *            pojo的类型
 * @para <ID> id的类型
 * 
 */
public abstract class BaseDaoImpl<T, ID extends Serializable>

implements BaseDao<T, ID> {

	@Resource
	private SessionFactory sessionFactory;

	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {

		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	public long save(T model) {
		return ((Long) this.getSession().save(model)).longValue();
	}

	public void saveOrUpdate(T model) {
		this.getSession().saveOrUpdate(model);
	}

	// 更新
	public void update(T model) {
		this.getSession().update(model);
	}

	/**
	 * 通过id查找
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(ID id) {
		return (T) this.getSession().get(getPersistentClass(), id);
	}

	/**
	 * 删除
	 */
	public void delete(T entity) {
		this.getSession().delete(entity);
	}

	/**
	 * 通过属性删除
	 */
	public void deleteByProperty(String propertyName, Object value) {
		String queryString = "delete from " + getPersistentClass().getName()
				+ " as model where model." + propertyName + "= ?";
		Query query = this.getSession().createQuery(queryString);
		query.setParameter(0, value);
		query.executeUpdate();
	}

	/**
	 * 分页查找所有的记录
	 * 
	 * @param page
	 *            要返回的页数
	 * @param pageSize
	 *            没有记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, int pageSize) {
		String queryString = "from " + getPersistentClass().getName();
		Query query = this.getSession().createQuery(queryString);
		int firstResult = (page - 1) * pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.list();
	}

	/**
	 * 统计所有记录的总数
	 * 
	 * @return 总数
	 */
	@SuppressWarnings("rawtypes")
	public int countAll() {
		String queryString = "select count(*) from "
				+ getPersistentClass().getName();
		Query query = this.getSession().createQuery(queryString);
		List list = query.list();
		Long result = (Long) list.get(0);
		return result.intValue();
	}

	/**
	 * find By Example
	 * 
	 * @param entity
	 * @return
	 */
	/*
	 * @SuppressWarnings("unchecked") public List<T> findByExample(T entity) {
	 * return this.getSession().findByExample(entity); }
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) this.getSession()
				.createQuery("from " + getPersistentClass().getName()).list();
	}

	/**
	 * 通过属性查找
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性的值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, Object value) {
		String queryString = "from " + getPersistentClass().getName()
				+ " as model where model." + propertyName + "= ?";
		Query query = this.getSession().createQuery(queryString);
		query.setParameter(queryString, value);
		return query.list();

	}

	/**
	 * 通过多个属性组合查询
	 * 
	 * @param propertyNames
	 *            属性名称数组
	 * @param values
	 *            对应于propertyNames的值 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertys(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}
		String queryString = strBuffer.toString();
		Query query = this.getSession().createQuery(queryString);
		setParameters(query, values);
		return query.list();

	}

	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if (paramlist[i] instanceof Date) {
					query.setTimestamp(i, (Date) paramlist[i]);
				} else {
					query.setParameter(i, paramlist[i]);
				}
			}
		}
	}

	/**
	 * 通过属性查找并分页
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @param page
	 *            页数
	 * @param pageSize
	 *            每页显示条数
	 */
	public List<T> findByProperty(String propertyName, Object value, int page,
			int pageSize) {
		return this.findByPropertys(new String[] { propertyName },
				new Object[] { value }, page, pageSize);
	}

	/**
	 * 通过多个属性组合查询
	 * 
	 * @param propertyNames
	 *            属性名称数组
	 * @param values
	 *            对应于propertyNames的值
	 * @param page
	 *            页数
	 * @param pageSize
	 *            每页显示数 return 匹配的结果 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize) {

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}
		String queryString = strBuffer.toString();

		int firstResult = (page - 1) * pageSize;

		Query query = this.getSession().createQuery(queryString);
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();
	}

	/**
	 * 通过属性统计数量
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 */
	public int countByProperty(String propertyName, Object value) {
		String[] propertyNames = new String[] { propertyName };
		Object[] values = new Object[] { value };
		return this.countByPropertys(propertyNames, values);
	}

	/**
	 * 通过多个属性统计数量
	 * 
	 * @param propertyNames
	 *            属性名称数组
	 * @param values
	 *            对应的属性值数组 return
	 */
	@SuppressWarnings("rawtypes")
	public int countByPropertys(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from "
				+ getPersistentClass().getName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append("=");
			strBuffer.append("? ");
		}

		String queryString = strBuffer.toString();
		Query query = this.getSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		List list = query.list();
		Long result = (Long) list.get(0);
		return result.intValue();
	}

	/**
	 * 查找T并通过某一属性排序
	 * 
	 * @param property
	 *            排序依据的顺序
	 * @param isSequence
	 *            是否顺序排序,false为倒序
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAndOrderByProperty(int firstResult, int fetchSize,
			String propertyName, boolean isSequence) {
		String queryString = "from " + getPersistentClass().getName()
				+ " as model order by model." + propertyName;
		if (isSequence == false) {
			queryString = queryString + " DESC";
		}

		Query queryObject = getSession().createQuery(queryString);
		queryObject.setFirstResult(firstResult);
		queryObject.setMaxResults(fetchSize);
		return queryObject.list();

	}

	/**
	 * 查找所有并通过某个属性排序
	 * 
	 * @param propertyName
	 *            排序依据的属性名称
	 * @param isSequence
	 *            是否顺序排列
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAllAndOrderByProperty(String propertyName,
			boolean isSequence) {
		String queryString = "from " + getPersistentClass().getName()
				+ " as model order by model." + propertyName;
		if (isSequence == false) {
			queryString = queryString + " DESC";
		}

		Query queryObject = getSession().createQuery(queryString);
		return queryObject.list();
	}

	// 刷新
	public void flush() {
		getSession().flush();
	}

	// 清理
	public void clear() {
		getSession().clear();
	}

	// 按照主键删除
	public void delete(ID id) {
		this.getSession().delete(id);
	}
}
