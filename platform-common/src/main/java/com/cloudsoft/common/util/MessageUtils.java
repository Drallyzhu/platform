package com.cloudsoft.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author zhujianrong
 */
public class MessageUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtils.class);

	private MessageUtils() {

	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param params
	 *            参数表
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，或resource key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object[] params) {
		if ((bundle == null) || (key == null)) {
			return key;
		}

		try {
			String message = bundle.getString(key);

			return formatMessage(message, params);
		} catch (MissingResourceException e) {
			LOGGER.error(e.getMessage(), e);
			return key;
		}
	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param param1
	 *            参数1
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，则返回<code>null</code>。如果resource
	 *         key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object param1) {
		return getMessage(bundle, key, new Object[] { param1 });
	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，则返回<code>null</code>。如果resource
	 *         key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object param1, Object param2) {
		return getMessage(bundle, key, new Object[] { param1, param2 });
	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，则返回<code>null</code>。如果resource
	 *         key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object param1, Object param2, Object param3) {
		return getMessage(bundle, key, new Object[] { param1, param2, param3 });
	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @param param4
	 *            参数4
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，则返回<code>null</code>。如果resource
	 *         key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object param1, Object param2, Object param3,
			Object param4) {
		return getMessage(bundle, key, new Object[] { param1, param2, param3, param4 });
	}

	/**
	 * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param bundle
	 *            resource bundle
	 * @param key
	 *            要查找的键
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @param param4
	 *            参数4
	 * @param param5
	 *            参数5
	 * @return key对应的字符串，如果key为<code>null</code>或resource
	 *         bundle为<code>null</code>，则返回<code>null</code>。如果resource
	 *         key未找到，则返回<code>key</code>
	 */
	public static String getMessage(ResourceBundle bundle, String key, Object param1, Object param2, Object param3,
			Object param4, Object param5) {
		return getMessage(bundle, key, new Object[] { param1, param2, param3, param4, param5 });
	}

	/*
	 * =========================================================================
	 * ===
	 */
	/* 以下是用MessageFormat格式化字符串的方法 */
	/*
	 * =========================================================================
	 * ===
	 */

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param params
	 *            参数表
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object[] params) {
		if ((message == null) || (params == null) || (params.length == 0)) {
			return message;
		}
		Object[] cloneParam = new Object[params.length];
		Object temp = null;
		for (int i = 0; i < params.length; i++) {
			temp = params[i];
			if(temp instanceof Date) {
				cloneParam[i] = DateUtils.format((Date)temp, DateUtils.DATE_AND_TIME);
			} else {
				cloneParam[i] = String.valueOf(temp);
			}
		}
		return MessageFormat.format(message, cloneParam);
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param param1
	 *            参数1
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object param1) {
		return formatMessage(message, new Object[] { param1 });
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object param1, Object param2) {
		return formatMessage(message, new Object[] { param1, param2 });
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object param1, Object param2, Object param3) {
		return formatMessage(message, new Object[] { param1, param2, param3 });
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @param param4
	 *            参数4
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object param1, Object param2, Object param3, Object param4) {
		return formatMessage(message, new Object[] { param1, param2, param3, param4 });
	}

	/**
	 * 使用<code>MessageFormat</code>格式化字符串.
	 *
	 * @param message
	 *            要格式化的字符串
	 * @param param1
	 *            参数1
	 * @param param2
	 *            参数2
	 * @param param3
	 *            参数3
	 * @param param4
	 *            参数4
	 * @param param5
	 *            参数5
	 * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
	 */
	public static String formatMessage(String message, Object param1, Object param2, Object param3, Object param4,
			Object param5) {
		return formatMessage(message, new Object[] { param1, param2, param3, param4, param5 });
	}
}
