package com.cloudsoft.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Xss过滤工具
 *
 */
public class JsoupUtils {
	/**
	 * 使用自带的basicWithImages 白名单
	 * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
	 * strike,strong,sub,sup,u,ul,img
	 * 以及a标签的href,img标签的src,align,alt,height,width,title属性
	 */
	private static final Whitelist whitelist = Whitelist.basicWithImages();

	static {
		whitelist.addAttributes(":all", "style");
		whitelist.addAttributes(":all", "align");
	}

	/*
	 * 配置过滤化参数,不对代码进行格式化
	 */
	private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

	public static String clean(String content) {
		return Jsoup.clean(content, "", whitelist, outputSettings);
	}


}
