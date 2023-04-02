package com.example.mybatis_plus.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Web参数处理工具类
 *
 * @author swh
 * @date 2017-05-23
 */
public class ParamUtils {

	private ParamUtils() {
	}

	/**
	 * 年月日期格式
	 */
	public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
	/**
	 * UTF-8编码名称(UTF-8)
	 */
	public static final String UTF8_NAME = "UTF-8";
	/** */
	private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

	/**
	 * 转换分布查询条件
	 *
	 * @param params 前端所有参数
	 * @return
	 */
	public static <T> Page<T> toPage(Class<T> clazz, JSONObject params) {
		Map<String, Object> map = params != null ? params.getInnerMap() : new HashMap<String, Object>();
		return toPage(clazz, map);
	}

	/**
	 * 转换分布查询条件
	 *
	 * @param map 前端所有参数
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> Page<T> toPage(Class<T> clazz, Map<?, ?> map) {
		Page<T> page = null;
		try {
			if (null != map) {
				// page = JsonUtil.map2obj((Map<?, ?>) map.get("page"), Page.class);
				page = map2obj(map, Page.class);
			}
		} catch (Exception e) {
			Log.error("Failed to toPage...", e);
		}
		if (null == page) {
			page = new Page<T>();
		}
		return page;
	}
	
	/**
	 * 转换Entity对象
	 *
	 * @param clazz Entity类型
	 * @param map   前端所有参数
	 * @return
	 */
	public static <T> T toEntity(Class<T> clazz, Map<?, ?> map) {
		try {
			if (null != map) {
				return map2obj(map, clazz);
			}
		} catch (Exception e) {
			Log.error("Failed to toPage...", e);
		}
		return null;
	}

	public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) {
		if (null != map) {
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) it.next();
				Object val = entry.getValue();
				if (val != null && "".equals(val.toString())) {
					it.remove();
				}
			}
		}
		return JsonUtil.map2obj(map, clazz);
	}

	/**
	 * 转换Entity对象
	 *
	 * @param clazz  Entity类型
	 * @param params 前端所有参数
	 * @return
	 */
	public static <T> T toEntity(Class<T> clazz, JSONObject params) {
		Map<String, Object> map = params != null ? params.getInnerMap() : new HashMap<String, Object>();
		return toEntity(clazz, map);
	}

	/**
	 * 获取查询条件
	 *
	 * @param params      前端所有参数
	 * @param addLikeKeys 需要like查询字段s
	 * @return
	 */
	public static Map<String, Object> toParams(JSONObject params, String... addLikeKeys) {
		Map<String, Object> map = params != null ? params.getInnerMap() : new HashMap<String, Object>();
		return toParams(map, addLikeKeys);
	}

	/**
	 * 获取查询条件
	 *
	 * @param params      前端所有参数
	 * @param addLikeKeys 需要like查询字段s
	 * @return
	 */
	public static Map<String, Object> toParams(Map<String, Object> params, String... addLikeKeys) {
		if (null != params && null != addLikeKeys) {
			for (String likeKey : addLikeKeys) {
				Object obj = params.get(likeKey);
				if (isNotBlank(obj)) {
					params.put(likeKey, addLike(obj));
				}
			}
		}
		return params;
	}

	/**
	 * 获取查询条件
	 *
	 * @param params           前端所有参数
	 * @param addLikeKeys      需要like查询字段，左右两边都加%
	 * @param addLikeRightKeys 需要like查询字段，右边加%
	 * @return
	 */
	public static Map<String, Object> toParams(Map<String, Object> params, String[] addLikeKeys, String[] addLikeRightKeys) {
		params = toParams(params, addLikeKeys);
		if (null != params) {
			if (null != addLikeRightKeys) {
				for (String likeRightKey : addLikeRightKeys) {
					Object obj = params.get(likeRightKey);
					if (isNotBlank(obj)) {
						params.put(likeRightKey, addLikeRight(obj));
					}
				}
			}
		}
		return params;
	}

	/**
	 * 格式化时间范围查询条件
	 * 
	 * @param params    参数集合
	 * @param addLikeKeys      需要like查询字段，左右两边都加%
	 * @return
	 */
	public static Map<String, Object> toParamDates(Map<String, Object> params, String... addLikeKeys) {
		params = toParams(params, addLikeKeys);
		return toParamDates(params, true, null, "startDate", "endDate");
	}

	/**
	 * 获取查询条件
	 *
	 * @param params           前端所有参数
	 * @param addLikeKeys      需要like查询字段，左右两边都加%
	 * @param addLikeRightKeys 需要like查询字段，右边加%
	 * @return
	 */
	public static Map<String, Object> toParamDates(Map<String, Object> params, String[] addLikeKeys, String[] addLikeRightKeys) {
		params = toParams(params, addLikeKeys, addLikeRightKeys);
		return toParamDates(params, true, null, "startDate", "endDate");
	}

	/**
	 * 格式化时间范围查询条件
	 *
	 * @param params    参数集合
	 * @param addLikeKeys      需要like查询字段，左右两边都加%
	 * @param startDateKey 开始时间key
	 * @param endDateKey   结束时间key
	 * @return
	 */
	public static Map<String, Object> toParamDates(Map<String, Object> params, String[] addLikeKeys, String startDateKey, String endDateKey) {
		params = toParams(params, addLikeKeys);
		return toParamDates(params, true, null, startDateKey, endDateKey);
	}

	/**
	 * 格式化时间范围查询条件
	 *
	 * @param params    参数集合
	 * @param addLikeKeys      需要like查询字段，左右两边都加%
	 * @param addLikeRightKeys 需要like查询字段，右边加%
	 * @param startDateKey 开始时间key
	 * @param endDateKey   结束时间key
	 * @return
	 */
	public static Map<String, Object> toParamDates(Map<String, Object> params, String[] addLikeKeys,  String[] addLikeRightKeys, String startDateKey, String endDateKey) {
		params = toParams(params, addLikeKeys, addLikeRightKeys);
		return toParamDates(params, true, null, startDateKey, endDateKey);
	}

	/**
	 * 格式化时间范围查询条件
	 * 
	 * @param params       参数集合
	 * @param ableEmpty    是否能为空
	 * @param days         天数
	 * @param startDateKey 开始时间key
	 * @param endDateKey   结束时间key
	 * @return
	 */
	public static Map<String, Object> toParamDates(Map<String, Object> params, boolean ableEmpty, Integer days, String startDateKey, String endDateKey) {
		startDateKey = isBlank(startDateKey) ? "startDate" : startDateKey;
		endDateKey = isBlank(endDateKey) ? "endDate" : endDateKey;
		days = days == null ? 7 : days.intValue();
		Date begin = paramTime(params, startDateKey, false);
		Date end = paramTime(params, endDateKey, true);
		if (null == end && !ableEmpty) {
			end = new Date();
		}
		if (null == begin && !ableEmpty) {
			begin = DateUtils.nextDay(end, -days);
		}
		if (null != begin) {
			params.put(startDateKey, begin);
		}
		if (null != end) {
			params.put(endDateKey, end);
		}
		return params;
	}

	/**
	 * 获取时间(yyyy-MM-dd HH:mm:ss)参数
	 *
	 */
	public static Date paramTime(Map<String, Object> params, String paramName, boolean isEndDate) {
		Date date = null;
		String str = toString(params, paramName);
		if (!StringUtils.isEmpty(str)) {
			if (str.length() == "yyyy-MM-dd".length()) {
				str = str + (isEndDate ? " 23:59:59" : " 00:00:00");
			} else if (str.length() == "yyyy-MM-dd HH".length()) {
				str = str + (isEndDate ? ":59:59" : ":00:00");
			} else if (str.length() == "yyyy-MM-dd HH:mm".length()) {
				str = str + (isEndDate ? ":59" : ":00");
			}
			date = DateUtils.parse(str, null);
			if (isEndDate) {
				date = DateUtils.addMilliSecond(date, 999);
			}
		}
		return date;
	}

	/**
	 * 获取查询条件对象
	 *
	 * @param clazz       类型
	 * @param params      查询参数
	 * @param addLikeKeys 需要like查询字段s
	 * @return
	 */
//	public static <T> QueryWrapper<T> toQueryWrapper(Class<T> clazz, JSONObject params, String... addLikeKeys) {
//		Map<String, Object> map = params != null ? params.getInnerMap() : new HashMap<String, Object>();
//		return toQueryWrapper(clazz, map, addLikeKeys);
//	}

	/**
	 * 获取查询条件对象
	 *
	 * @param clazz       类型
	 * @param params      查询参数
	 * @param addLikeKeys 需要like查询字段s
	 * @return
	 */
//	public static <T> QueryWrapper<T> toQueryWrapper(Class<T> clazz, Map<String, Object> params, String... addLikeKeys) {
//		QueryWrapper<T> qw = new QueryWrapper<>();
//		Map<String, Object> dbParams = new HashMap<String, Object>();
//		if (null != params) {
//			// 排除非Entity属性的字段
//			Map<String, Field> fieldsMap = ClassUtil.getClassFields(clazz, true);
//			if (null != fieldsMap && fieldsMap.size() > 0) {
//				for (String k : params.keySet()) {
//					String dbKey = camelhumpToUnderline(k);
//					if (fieldsMap.containsKey(k) || k.equals(dbKey)) {
//						dbParams.put(dbKey, params.get(k));
//					}
//				}
//			}
//			// 模糊查询
//			if (dbParams != null && dbParams.size() > 0 && null != addLikeKeys) {
//				for (String likeKey : addLikeKeys) {
//					if (params.containsKey(likeKey)) {
//						Object obj = params.get(likeKey);
//						qw.like(isNotBlank(obj), camelhumpToUnderline(likeKey), obj);
//						params.remove(likeKey);
//					}
//				}
//			}
//			// 等值查询
//			if (dbParams != null && dbParams.size() > 0) {
//				qw.allEq(true, dbParams, false);
//				// qw.allEq((k, v) -> (k.indexOf("a") >= 0), params, false);
//			}
//		}
//		return qw;
//	}

	/**
	 * 判断不为空
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}

	/**
	 * 判断为空
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		if (obj != null) {
			return StringUtils.isBlank(obj.toString());
		}
		return true;
	}

	/**
	 * 前后添加“%”
	 *
	 * @param obj
	 * @return
	 */
	public static String addLike(Object obj) {
		if (obj != null) {
			return "%" + obj.toString() + "%";
		}
		return null;
	}

	/**
	 * 前后添加“%”
	 *
	 * @param obj
	 * @return
	 */
	public static String addLikeRight(Object obj) {
		if (obj != null) {
			return obj.toString() + "%";
		}
		return null;
	}

	/**
	 * 驼峰转下划线
	 *
	 * @param str
	 * @return
	 */
	public static String camelhumpToUnderline(String str) {
		Matcher matcher = HUMP_PATTERN.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 转换String
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static String toString(Map<?, ?> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return obj.toString();
			}
		}
		return null;
	}
	
	/**
	 * 转换String
	 *
	 * @param json
	 * @param key
	 * @return
	 */
	public static String toString(JSONObject json, String key) {
		if (null != json) {
			return toString(json.getInnerMap(), key);
		}
		return null;
	}

	/**
	 * 转换Integer
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Integer toInteger(Map<?, ?> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return Integer.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 转换Integer
	 *
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer toInteger(JSONObject json, String key) {
		if (null != json) {
			return toInteger(json.getInnerMap(), key);
		}
		return null;
	}

	/**
	 * 转换Float
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Float toFloat(Map<?, ?> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return Float.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 转换Float
	 *
	 * @param json
	 * @param key
	 * @return
	 */
	public static Float toFloat(JSONObject json, String key) {
		if (null != json) {
			return toFloat(json.getInnerMap(), key);
		}
		return null;
	}

	/**
	 * 转换Double
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Double toDouble(Map<?, ?> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return Double.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 转换Double
	 *
	 * @param json
	 * @param key
	 * @return
	 */
	public static Double toDouble(JSONObject json, String key) {
		if (null != json) {
			return toDouble(json.getInnerMap(), key);
		}
		return null;
	}

	/**
	 * 转换Boolean
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Boolean toBoolean(Map<?, ?> map, String key) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return Boolean.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 转换Date类型
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	public static Date toDate(Map<?, ?> map, String key) {
		return toDate(map, key, null);
	}

	/**
	 * 转换Date类型
	 *
	 * @param map
	 * @param key
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date toDate(Map<?, ?> map, String key, String pattern) {
		if (null != map && map.containsKey(key)) {
			Object obj = map.get(key);
			if (null != obj) {
				return toDate(obj.toString(), pattern);
			}
		}
		return null;
	}

	/**
	 * 转换Date类型
	 *
	 * @param date
	 * @return
	 */
	public static Date toDate(String date) {
		return toDate(date, null);
	}

	/**
	 * 转换Date类型
	 *
	 * @param date
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date toDate(String date, String pattern) {
		if (null != date) {
			if (date.length() > "yyyy-MM-dd".length()) {
				return DateUtils.parse(date, null);
			} else {
				return DateUtils.parseDate(date);
			}
		}
		return null;
	}

	/**
	 * Url地址编码
	 *
	 * <pre>
	 * URLEncoder.encode(str, &quot;UTF-8&quot;)
	 * </pre>
	 *
	 * @param url 地址
	 * @return 编码后的地址
	 */
	public static String urlEncode(String url) {
		if (null != url) {
			try {
				return URLEncoder.encode(url, UTF8_NAME);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

	/**
	 * Url地址编码
	 *
	 * <pre>
	 * URLEncoder.encode(str, &quot;UTF-8&quot;)
	 * </pre>
	 *
	 * @param url 地址
	 * @return 编码后的地址
	 */
	public static String urlDecode(String url) {
		if (null != url) {
			try {
				return URLDecoder.decode(url, UTF8_NAME);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}

	/**
	 * 根据年月返回指定月开始与下一月开始时间
	 *
	 * @param month 月份(格式yyyyMM)
	 * @return Date{指定月份开始时间,下一月开始时间}
	 */
	public static Date[] monthBeginAndEnd(String month) {
		try {
			Date begin = MONTH_FORMAT.parse(month);// 指定月时间
			Date[] ds = new Date[2];
			ds[0] = begin;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(begin);
			calendar.add(Calendar.MONTH, 1);
			ds[1] = calendar.getTime();
			return ds;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 字符串转换为int型set
	 *
	 * @param str 字符串
	 * @return
	 */
	public static Set<Integer> intSet(String str) {
		if (null != str && str.length() > 0) {
			Set<Integer> set = new HashSet<Integer>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.matches("[0-9-]{0,11}")) {
					try {
						set.add(Integer.parseInt(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!set.isEmpty()) {
				return set;
			}
		}
		return null;
	}

	/**
	 * 字符串转换为int型数组
	 *
	 * @param str 字符串
	 * @return
	 */
	public static Integer[] intArr(String str) {
		if (null != str && str.length() > 0) {
			List<Integer> list = new ArrayList<Integer>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.matches("[0-9-]{0,11}")) {
					try {
						Integer i = Integer.parseInt(s);
						list.add(i);
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list.toArray(new Integer[list.size()]);
			}
		}
		return null;
	}

	/**
	 * 字符串转换为long型集合
	 *
	 * @param str 字符串
	 * @return long型集合/null
	 */
	public static List<Long> longList(String str) {
		if (null != str && str.length() > 0) {
			List<Long> list = new ArrayList<Long>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.matches("[0-9-]{0,20}")) {
					try {
						list.add(Long.parseLong(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}

	/**
	 * 字符串转换为long型数组
	 *
	 * @param str 字符串
	 * @return long型数组/null
	 */
	public static Long[] longArr(String str) {
		List<Long> list = longList(str);
		if (null != list) {
			return list.toArray(new Long[list.size()]);
		} else {
			return null;
		}
	}

	/**
	 * 获取客户端IP
	 *
	 * @param request HttpServletRequest对象
	 * @return IP地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 页面输出错误信息
	 *
	 * @param response HttpServletResponse对象
	 * @param msg      输出内容
	 * @throws IOException
	 */
	public static void printError(HttpServletResponse response, String msg) throws IOException {
		print(response, msg);
	}

	/**
	 * 页面直接输出明文内容
	 *
	 * @param response HttpServletResponse对象
	 * @param msg      输出内容(text/html; charset=UTF-8)
	 */
	public static void print(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding(UTF8_NAME);
		response.getWriter().print(msg);
	}

	/**
	 * 页面直接输出明文内容
	 *
	 * @param response  HttpServletResponse对象
	 * @param msg       输出内容
	 * @param character 编码(UTF-8,GBK,...,缺省UTF-8)
	 * @param type      内容格式(html,plain,xml,json,...,缺省html)
	 */
	public static void print(HttpServletResponse response, String msg, String character, String type) {
		try {
			if (null == character) {
				character = UTF8_NAME;
			}
			if (null == type) {
				type = "html";
			}
			response.setContentType("text/" + type + "; charset=" + character);
			response.setCharacterEncoding(character);
			response.getWriter().print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除http特殊字符(",&gt;,&lt;)
	 *
	 * @param str 字符串
	 * @return 移除特殊字符后的字符串
	 */
	public static String htmlRmv(String str) {
		if (null != str) {
			str = str.replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "");
			return htmoXssClean(str);
		} else {
			return str;
		}
	}

	/**
	 * 移除http特殊字符
	 *
	 * @param value 字符串
	 * @return 移除特殊字符后的字符串
	 */
	public static String htmoXssClean(String value) {
		if (value != null) {
			value = value.replaceAll("", "");
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of
			// e­xpression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) e­xpressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid e­xpression(...) e­xpressions
			scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... e­xpressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... e­xpressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= e­xpressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <img tag
			scriptPattern = Pattern.compile("<img(.*?)>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onerror= e­xpressions
			scriptPattern = Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	/**
	 * 获取参数字符串
	 *
	 * @param request   HttpServletRequest对象
	 * @param paramName 参数名称
	 * @return
	 */
	public static String getStr(HttpServletRequest request, String paramName) {
		return getStr(request, paramName, null);
	}

	/**
	 * 获取参数字符串
	 *
	 * @param request   HttpServletRequest对象
	 * @param paramName 参数名称
	 * @param utf8      是否utf8编码字符串
	 * @return
	 */
	public static String getStr(HttpServletRequest request, String paramName, boolean utf8) {
		return getStr(request, paramName, utf8 ? UTF8_NAME : null);
	}

	/**
	 * 获取参数字符串
	 *
	 * @param request   HttpServletRequest对象
	 * @param paramName 参数名称
	 * @param charset   编码字符串
	 * @return
	 */
	public static String getStr(HttpServletRequest request, String paramName, String charset) {
		String str = request.getParameter(paramName);
		if (null == str) {
			Object o = request.getAttribute(paramName);
			if (null != o) {
				str = o.toString();
			}
		}
		if (null != str) {
			str = htmlRmv(str.trim());
			if (str.length() > 0) {
				if (null != charset) {
					try {
						str = new String(str.getBytes("ISO-8859-1"), charset);
					} catch (UnsupportedEncodingException e) {
					}
				}
				return str;
			}
		}
		return null;
	}

	/**
	 * 生成32位MD5密文
	 * 
	 * <pre>
	 * org.apache.commons.codec.digest.DigestUtils
	 * </pre>
	 * 
	 * @param str 明文
	 * @return 密文
	 */
//	public static String md5(String str) {
//		if (null != str) {
//			return DigestUtils.md5Hex(str);
//		}
//		return null;
//	}
}
