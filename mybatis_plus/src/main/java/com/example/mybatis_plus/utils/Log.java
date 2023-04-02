package com.example.mybatis_plus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * <pre>
 * 根据配置的info、debug开关打印日志
 * </pre>
 * 
 * @author swh
 * @date 2017-05-01
 * 
 */
public class Log {

	private Log() {
	}

	private static final Logger logger = LoggerFactory.getLogger(Log.class);
	private static final String SYS = "[API]";
	private static final String ERROR = "操作失败";

	/**
	 * 打印info级别日志
	 * 
	 * @param log 日志内容
	 */
	public static void info(String log) {
		logger.info(SYS + log);
	}

	/**
	 * 打印error级别日志
	 * 
	 * @param log 日志内容
	 */
	public static void error(String log) {
		logger.error(SYS + log);
	}

	/**
	 * 打印error级别日志
	 * 
	 * @param log 日志内容
	 * @param e   异常
	 */
	public static void error(String log, Throwable e) {
		logger.error(SYS + log, e);
	}

	/**
	 * 打印error级别日志
	 * 
	 * @param log 日志内容
	 * @param e   异常
	 */
	public static void error(Throwable e) {
		logger.error(SYS + ERROR, e);
	}

}
