package com.springmvc.base.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EnumSet;
import java.util.Properties;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * 
 * Summary : hibernate 针对 enum 持久化的自定义映射类型
 * 
 * 
 * @author 
 * 
 * @since 2012-12-25
 */
public class EnumConverterType implements UserType, ParameterizedType,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9172959906477688679L;

	private static final Log log = LogFactory.getLog( EnumConverterType.class );

	private String enumClass;

	@SuppressWarnings("rawtypes")
	private Class clazz;
	private static final int[] SQL_TYPES = { Types.INTEGER };

	/**
	 * Initial enum class
	 * 
	 * @return initialize class success or not
	 */
	private boolean initClass() {
		if (clazz == null) {
			try {
				clazz = Class.forName(enumClass);
				if (!ClassUtils.isAssignable(clazz, EnumRestrictor.class)) {
					throw new Exception(new StringBuilder("given class ")
							.append(enumClass)
							.append("should be a inherition of ")
							.append(EnumRestrictor.class.getName()).toString());
				}
				return true;
			} catch (ClassNotFoundException e) {
				if (log.isDebugEnabled()) {
					log.debug("Can not found class: " + enumClass, e);
				}
				return false;
			} catch (Exception e) {
				if (log.isDebugEnabled()) {
					log.debug("exception in initialing " + clazz.getName(), e);
				}
				return false;
			}
		}
		return true;
	}

	public Object assemble(Serializable serializable, Object o)
			throws HibernateException {
		return serializable;
	}

	public Object deepCopy(Object o) throws HibernateException {
		return o;
	}

	public Serializable disassemble(Object o) throws HibernateException {
		return (Serializable) o;
	}

	public boolean equals(Object o, Object o1) throws HibernateException {
		return o == o1 || !(o == null || o1 == null) && o.equals(o1);
	}

	public int hashCode(Object o) throws HibernateException {
		return o.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o)
			throws HibernateException, SQLException {
		final int val = resultSet.getInt(strings[0]);
		Object obj = null;
		if (!resultSet.wasNull() && initClass()) {

			@SuppressWarnings("unchecked")
			EnumSet enumSet = EnumSet.allOf((Class<Enum>) clazz);
			for (Object object : enumSet) {
				EnumRestrictor e = (EnumRestrictor) object;
				if (e.getValue() == val) {
					obj = e;
				}
			}
		}
		return obj;
	}

	public void nullSafeSet(PreparedStatement preparedStatement, Object value,
			int index) throws HibernateException, SQLException {
		if (null == value) {
			preparedStatement.setNull(index, Types.INTEGER);
		} else {
			if (initClass()) {
				if (value instanceof Integer) {
					preparedStatement.setInt(index, (Integer) value);
				} else {
					try {
						preparedStatement.setInt(index,
								((EnumRestrictor) value).getValue());
					} catch (Exception e) {
						throw new HibernateException(
								new StringBuilder("parameter in ")
										.append(--index)
										.append(" of current HQL give error in casting of ").append(EnumRestrictor.class.getName())
										.toString());
					}
				}
			} else {
				preparedStatement.setNull(index, Types.INTEGER);
			}
		}
	}

	public Object replace(Object o, Object arg1, Object arg2) {
		return o;
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		if (initClass()) {
			return clazz;
		}
		return null;
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public void setParameterValues(Properties properties) {
		enumClass = properties.getProperty("enumClass");
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		
	}
}