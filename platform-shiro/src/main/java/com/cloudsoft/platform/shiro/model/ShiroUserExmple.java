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
public class ShiroUserExmple implements Specification<ShiroUser> {
    private static final long serialVersionUID = -507047177168789533L;

    private ShiroUser shiroUser;

    private ShiroUserExmple(ShiroUser shiroUser){

        this.shiroUser = shiroUser;
    }

    @Override
    public Predicate toPredicate(Root<ShiroUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(shiroUser.getLoginName())){
            list.add(cb.like(root.get("loginName"), "%"+ shiroUser.getLoginName() +"%"));
        }
        if (StringUtils.isNotBlank(shiroUser.getNickName())){
            list.add(cb.like(root.get("nickName"), "%"+ shiroUser.getNickName() +"%"));
        }
        if (StringUtils.isNotBlank(shiroUser.getPhone())){
            list.add(cb.like(root.get("phone"), "%"+ shiroUser.getPhone() +"%"));
        }
        if (StringUtils.isNotBlank(shiroUser.getPhone())){
            list.add(cb.like(root.get("phone"), "%"+ shiroUser.getPhone() +"%"));
        }
        if (StringUtils.isNotBlank(shiroUser.getPId())){
            list.add(cb.equal(root.get("pId"), shiroUser.getPId()));
        }
        if (StringUtils.isNotBlank(shiroUser.getSex())){
            list.add(cb.equal(root.get("sex"), shiroUser.getSex()));
        }
        if (StringUtils.isNotBlank(shiroUser.getStatus())){
            list.add(cb.equal(root.get("status"), shiroUser.getStatus()));
        }
        if (shiroUser.getLastLoginTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("lastLoginTime"), shiroUser.getLastLoginTime()));
        }
        if (shiroUser.getCreateTime() != null){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), shiroUser.getCreateTime()));
        }
        if (shiroUser.getUpdateTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("updateTime"), shiroUser.getUpdateTime()));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }
    public static ShiroUserExmple createExample(ShiroUser shiroUser){
        return new ShiroUserExmple(shiroUser);
    }

}
