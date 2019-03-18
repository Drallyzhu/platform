package com.cloudsoft.platform.shiro.model.dto;

import lombok.Data;

import java.io.Serializable;

/**   
 * @ClassName:  BindResourceDto   
 * @Description:TODO  
 * @author: zhujianrong
 *
 */
@Data
public class BindResourceDto implements Serializable {
	private static final long serialVersionUID = -4064346904822378765L;
	/** id */
	private String id;
	
	/** 父id */
	private String parentId;
	
	/** 资源名称 */
	private String resourceName;
	
	/** 是否勾选 */
	private boolean checked = false;
	

}
