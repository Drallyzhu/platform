package com.cloudsoft.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类()
 * @author zhujianrong
 */
public class Log {

	public static Logger get(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger get(String name) {
		return LoggerFactory.getLogger(name);
	}

	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[2].getClassName());
	}

	public static void trace(String format, Object... arguments) {
		trace(innerGet(), format, arguments);
	}

	public static void trace(Logger log, String format, Object... arguments) {
		log.trace(format, arguments);
	}

	public static void debug(String format, Object... arguments) {
		debug(innerGet(), format, arguments);
	}

	public static void debug(Logger log, String format, Object... arguments) {
		log.debug(format, arguments);
	}

	public static void info(String format, Object... arguments) {
		info(innerGet(), format, arguments);
	}

	public static void info(Logger log, String format, Object... arguments) {
		log.info(format, arguments);
	}

	public static void warn(String format, Object... arguments) {
		warn(innerGet(), format, arguments);
	}

	public static void warn(Logger log, String format, Object... arguments) {
		log.warn(format, arguments);
	}

	public static void warn(Throwable e, String format, Object... arguments) {
		warn(innerGet(), e, format(format, arguments));
	}

	public static void warn(Logger log, Throwable e, String format, Object... arguments) {
		log.warn(format(format, arguments), e);
	}

	public static void error(String format, Object... arguments) {
		error(innerGet(), format, arguments);
	}

	public static void error(Logger log, String format, Object... arguments) {
		log.error(format, arguments);
	}

	public static void error(Throwable e, String format, Object... arguments) {
		error(innerGet(), e, format(format, arguments));
	}

	public static void error(Logger log, Throwable e, String format, Object... arguments) {
		log.error(format(format, arguments), e);
	}

	private static String format(String template, Object... values) {
		return String.format(template.replace("{}", "%s"), values);
	}

	private static Logger innerGet() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[3].getClassName());
	}

}