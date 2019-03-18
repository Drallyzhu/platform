package com.cloudsoft.platform.shiro.model.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MenuDto implements Serializable {
	private static final long serialVersionUID = -127094621650529246L;
	/** id*/
	private String id;
	/** 父ID（所属父级，0:没有父级）*/
	private String parentId;
	/** 资源名称*/
	private String resourceName;

	/** 资源内容*/
	private Meta meta;
	@Data
	public class Meta {
		/** 资源标记(唯一,通过该字段可以找到相应组件,可以用在按钮标识，可以用到一些其他标识，可以和pageUrl结合)*/
		private String resourceMarking;
		/** 授权码(shiro 里面的 注入标签@RequiresPermissions("xxxx"))*/
		private String grandKey;
		/** 资源访问路径*/
		private String resourceUrl;
		/** 排序*/
		private Integer sort;
		/** 资源类型(0代表按钮 ,1代表导航（父），2代表一级菜单，3代表二级菜单，4代表三级菜单，5代表四级菜单，6代表五级菜单，7代表六级菜单)*/
		private Integer type;
		/** 前端使用path*/
		private String path;
		/** 前端使用component*/
		private String component;
		/** 前端使用title*/
		private String title;
		/** 前端使用icon*/
		private String icon;
		/** 状态(VALID:有效  INVALID:无效)*/
		private String status;
	}

}
