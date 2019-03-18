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
public class ShiroRoleExmple implements Specification<ShiroRole> {
    private static final long serialVersionUID = -507047177168789533L;

    private ShiroRole shiroRole;

    private ShiroRoleExmple(ShiroRole shiroRole){

        this.shiroRole = shiroRole;
    }

    @Override
    public Predicate toPredicate(Root<ShiroRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(shiroRole.getRoleName())){
            list.add(cb.like(root.get("roleName"), "%"+ shiroRole.getRoleName() +"%"));
        }
        if (StringUtils.isNotBlank(shiroRole.getDescription())){
            list.add(cb.like(root.get("description"), "%"+ shiroRole.getDescription() +"%"));
        }
        if (StringUtils.isNotBlank(shiroRole.getId())){
            list.add(cb.equal(root.get("id"), shiroRole.getId()));
        }
        if (StringUtils.isNotBlank(shiroRole.getStatus())){
            list.add(cb.equal(root.get("status"), shiroRole.getStatus()));
        }
        if (shiroRole.getCreateTime() != null){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), shiroRole.getCreateTime()));
        }
        if (shiroRole.getUpdateTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("updateTime"), shiroRole.getUpdateTime()));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }
    public static ShiroRoleExmple createExample(ShiroRole shiroRole){
        return new ShiroRoleExmple(shiroRole);
    }

}
