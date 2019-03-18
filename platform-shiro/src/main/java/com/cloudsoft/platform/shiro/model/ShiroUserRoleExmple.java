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
public class ShiroUserRoleExmple implements Specification<ShiroUserRole> {
    private static final long serialVersionUID = -507047177168789533L;

    private ShiroUserRole shiroUserRole;

    private ShiroUserRoleExmple(ShiroUserRole shiroUserRole){

        this.shiroUserRole = shiroUserRole;
    }

    @Override
    public Predicate toPredicate(Root<ShiroUserRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(shiroUserRole.getId())){
            list.add(cb.equal(root.get("id"), shiroUserRole.getId()));
        }
        if (StringUtils.isNotBlank(shiroUserRole.getRoleId())){
            list.add(cb.equal(root.get("roleId"), shiroUserRole.getRoleId()));
        }
        if (StringUtils.isNotBlank(shiroUserRole.getUserId())){
            list.add(cb.equal(root.get("userId"), shiroUserRole.getUserId()));
        }
        if (StringUtils.isNotBlank(shiroUserRole.getStatus())){
            list.add(cb.equal(root.get("status"), shiroUserRole.getStatus()));
        }
        if (shiroUserRole.getCreateTime() != null){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), shiroUserRole.getCreateTime()));
        }
        if (shiroUserRole.getUpdateTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("updateTime"), shiroUserRole.getUpdateTime()));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }
    public static ShiroUserRoleExmple createExample(ShiroUserRole shiroUserRole){
        return new ShiroUserRoleExmple(shiroUserRole);
    }

}
