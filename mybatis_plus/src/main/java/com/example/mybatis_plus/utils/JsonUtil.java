package com.example.mybatis_plus.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	public static JSONObject ObjectToJson(Object object) {
		try {
			if (object == null || object.toString().length() == 0) {
				return null;
			}
			return JSONObject.parseObject(JSONObject.toJSONString(object));
		} catch (JSONException e) {
			return JSONObject.parseObject(JSON.parse(object.toString()).toString());
		}
	}

	public static <T> JSONArray ListToJSONArray(List<T> data) {
		return JSONArray.parseArray(JSON.toJSONString(data));
	}

	public static JSONObject ObjectToResponseObject(Object object) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 1);
		jsonObject.put("message", "success");
		jsonObject.put("data", ObjectToJson(object));
		return jsonObject;
	}

	public static <T> JSONObject ListToResponseObject(List<T> data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", 1);
		jsonObject.put("message", "success");
		jsonObject.put("data", ListToJSONArray(data));
		return jsonObject;
	}

	public static JSONObject ObjectToResponseObject(int code, String message, Object object) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		jsonObject.put("data", ObjectToJson(object));
		return jsonObject;
	}

	public static <T> JSONObject ListToResponseObject(int code, String message, List<T> data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		jsonObject.put("data", ListToJSONArray(data));
		return jsonObject;
	}

	public static String obj2json(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static <T> T json2obj(String jsonStr, Class<T> clazz) {
		try {
			if (jsonStr == null || jsonStr.length() == 0) {
				return null;
			}
			return JSON.parseObject(jsonStr, clazz);
		} catch (JSONException e) {
			return JSONObject.parseObject(JSON.parse(jsonStr).toString(), clazz);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) {
		return json2obj(jsonStr, Map.class);
	}

	public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(map), clazz);
	}

	public static <T> T jsonobject2obj(JSONObject obj, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(obj), clazz);
	}

	public static <T> T objectToClass(Object obj, Class<T> clazz) {
		try {
			if (obj == null || obj.toString().length() == 0) {
				return null;
			}
			if(obj instanceof String) {
				return JSONObject.parseObject(JSON.parse(obj.toString()).toString(), clazz);
			} else {
				return JSONObject.parseObject(JSON.toJSONString(obj), clazz);
			}
		} catch (JSONException e) {
			return JSONObject.parseObject(JSON.parse(obj.toString()).toString(), clazz);
		}
	}

	public static <T> List<T> toObjectList(Object obj, Class<T> clazz) {
		List<?> list = objectToClass(obj, List.class);
		return toObjectList(list, clazz);
	}

	public static <T> List<T> toObjectList(List<?> list, Class<T> clazz) {
		if (null != list) {
			List<T> rs = new ArrayList<T>();
			for (Object obj : list) {
				rs.add(objectToClass(obj, clazz));
			}
			return rs;
		}
		return null;
	}

}