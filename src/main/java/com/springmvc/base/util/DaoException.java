/**
 * 
 */
package com.springmvc.base.util;

import org.springframework.core.NestedRuntimeException;

/**
 * @author 
 * @date Nov 4, 2011
 */
@SuppressWarnings("serial")
public class DaoException extends NestedRuntimeException {

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String msg, Throwable obj) {
		super(msg, obj);
	}
}
