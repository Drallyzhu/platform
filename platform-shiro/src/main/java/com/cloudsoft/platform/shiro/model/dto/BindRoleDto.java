package com.cloudsoft.platform.shiro.model.dto;

import lombok.Data;
import java.io.Serializable;
@Data
public class BindRoleDto implements Serializable {
	private static final long serialVersionUID = -849717080251607917L;

	/** id*/
    private String id;

    /** 角色名称*/
    private String roleName;
    
    /** 是否选中 */
    private boolean checked = false;


    
}
