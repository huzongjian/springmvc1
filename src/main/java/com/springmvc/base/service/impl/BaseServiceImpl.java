package com.springmvc.base.service.impl;

import java.io.Serializable;
import java.util.List;

import com.springmvc.base.dao.BaseDao;
import com.springmvc.base.service.BaseService;

public abstract class BaseServiceImpl<T, ID extends Serializable> implements
		BaseService<T, ID> {

	public abstract BaseDao<T, ID> getDao();

	public List<T> findAll() {
		// TODO Auto-generated method stub
		return getDao().findAll();
	}

	public List<T> findAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return getDao().findAll(page, pageSize);
	}

	public long save(T entity) {
		// TODO Auto-generated method stub
		return getDao().save(entity);
	}

	public void delete(T entity) {
		// TODO Auto-generated method stub
		getDao().delete(entity);

	}

	public void deleteByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		getDao().deleteByProperty(propertyName, value);

	}

	public List<T> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return getDao().findByProperty(propertyName, value);
	}

	public List<T> findByPropertys(String[] propertyNames, Object[] values) {
		// TODO Auto-generated method stub
		return getDao().findByPropertys(propertyNames, values);
	}

	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		return getDao().findByPropertys(propertyNames, values, page, pageSize);
	}

	public List<T> findByProperty(String propertyName, Object value, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return getDao().findByProperty(propertyName, value, page, pageSize);
	}

	public int countAll() {
		// TODO Auto-generated method stub
		return getDao().countAll();
	}

	public int countByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return getDao().countByProperty(propertyName, value);
	}

	public int countByPropertys(String[] propertyNames, Object[] values) {
		// TODO Auto-generated method stub
		return getDao().countByPropertys(propertyNames, values);
	}

	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		getDao().saveOrUpdate(entity);

	}

	public void update(T entity) {
		// TODO Auto-generated method stub
		getDao().update(entity);
	}

	public List<T> findAndOrderByProperty(int firstResult, int fetchSize,
			String propertyName, boolean isSequence) {
		// TODO Auto-generated method stub
		return getDao().findAndOrderByProperty(firstResult, fetchSize,
				propertyName, isSequence);
	}

	public List<T> findAllAndOrderByProperty(String propertyName,
			boolean isSequence) {
		// TODO Auto-generated method stub
		return getDao().findAllAndOrderByProperty(propertyName, isSequence);
	}

	public void flush() {
		// TODO Auto-generated method stub
		getDao().flush();
	}

	public void clear() {
		// TODO Auto-generated method stub
		getDao().clear();
	}

	public void delete(ID id) {
		// TODO Auto-generated method stub
		getDao().delete(id);
	}

	public T get(ID id) {
		// TODO Auto-generated method stub
		return getDao().get(id);
	}

}
