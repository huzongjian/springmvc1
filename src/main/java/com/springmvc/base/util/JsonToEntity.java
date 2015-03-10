package com.springmvc.base.util;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

public class JsonToEntity {

	/**
	 * 
	 * @Title: toJSONAsString
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param obj
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @author HuZongjian
	 * @date 2014-7-8 下午3:18:57
	 */
	public static String toJSONAsString(Object obj) {
		try {
			return JSONMapper.toJSON(obj).render(false);
		} catch (MapperException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String jsonStr, Class<T> targetClass)
			throws TokenStreamException, RecognitionException, MapperException {
		JSONValue jv = new JSONParser(new StringReader(jsonStr)).nextValue();
		return (T) JSONMapper.toJava(jv, targetClass);
	}

}
