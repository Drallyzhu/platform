package com.cloudsoft.platform.shiro.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhujianrong
 */
public class ShiroRoleResourceExmple implements Specification<ShiroRoleResource> {
    private static final long serialVersionUID = -507047177168789533L;

    private ShiroRoleResource shiroRoleResource;

    private ShiroRoleResourceExmple(ShiroRoleResource shiroRoleResource){

        this.shiroRoleResource = shiroRoleResource;
    }

    @Override
    public Predicate toPredicate(Root<ShiroRoleResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(shiroRoleResource.getId())){
            list.add(cb.equal(root.get("id"), shiroRoleResource.getId()));
        }
        if (StringUtils.isNotBlank(shiroRoleResource.getResourceId())){
            list.add(cb.equal(root.get("resourceId"), shiroRoleResource.getResourceId()));
        }
        if (StringUtils.isNotBlank(shiroRoleResource.getRoleId())){
            list.add(cb.equal(root.get("roleId"), shiroRoleResource.getRoleId()));
        }
        if (StringUtils.isNotBlank(shiroRoleResource.getStatus())){
            list.add(cb.equal(root.get("status"), shiroRoleResource.getStatus()));
        }
        if (shiroRoleResource.getCreateTime() != null){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), shiroRoleResource.getCreateTime()));
        }
        if (shiroRoleResource.getUpdateTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("updateTime"), shiroRoleResource.getUpdateTime()));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }
    public static ShiroRoleResourceExmple createExample(ShiroRoleResource shiroRoleResource){
        return new ShiroRoleResourceExmple(shiroRoleResource);
    }

}
