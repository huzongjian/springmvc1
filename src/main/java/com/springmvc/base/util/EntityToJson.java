package com.springmvc.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.json.JSONObject;

public class EntityToJson {
	/**
	 * 
	 * @Title: entityToJSON
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param entity
	 * @param @param json 设定文件
	 * @return void 返回类型
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 上午9:28:01
	 */
	@SuppressWarnings("rawtypes")
	public static void entityToJSON(Object entity, JSONObject json) {

		Class clzss = entity.getClass();

		Field[] fields = clzss.getDeclaredFields();

		try {
			for (Field field : fields) {
				// 确认是否带有JSONValue注解
				if (field.getAnnotation(JsonValue.class) != null) {
					json.put(field.getName(),
							getFieldValue(entity, field.getName()));
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取字段的值
	 * 
	 * @param data
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getFieldValue(Object data, String fieldName) {

		StringBuilder sb = new StringBuilder();

		Class clzss = data.getClass();

		// 将字段首字母大写
		String firstWord = fieldName.substring(0, 1).toUpperCase();
		sb.append(firstWord);
		sb.append(fieldName.substring(1, fieldName.length()));

		final String methodName = "get" + sb.toString();

		Method[] methods = clzss.getDeclaredMethods();
		try {
			for (Method method : methods) {
				// 调用对应的方法
				if (methodName.equals(method.getName())) {
					return method.invoke(data, new Object[] {});
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return null;

	}

}
