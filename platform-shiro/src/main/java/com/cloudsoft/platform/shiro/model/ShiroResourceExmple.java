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
public class ShiroResourceExmple implements Specification<ShiroResource> {
    private static final long serialVersionUID = -507047177168789533L;

    private ShiroResource shiroResource;

    private ShiroResourceExmple(ShiroResource shiroResource){

        this.shiroResource = shiroResource;
    }

    @Override
    public Predicate toPredicate(Root<ShiroResource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(shiroResource.getId())){
            list.add(cb.equal(root.get("id"), shiroResource.getId()));
        }
        if (StringUtils.isNotBlank(shiroResource.getParentId())){
            list.add(cb.equal(root.get("parentId"), shiroResource.getParentId()));
        }
        if (StringUtils.isNotBlank(shiroResource.getComponent())){
            list.add(cb.like(root.get("component"), "%"+ shiroResource.getComponent() +"%"));
        }
        if (StringUtils.isNotBlank(shiroResource.getGrandKey())){
            list.add(cb.equal(root.get("grandKey"), shiroResource.getGrandKey()));
        }
        if (StringUtils.isNotBlank(shiroResource.getIcon())){
            list.add(cb.like(root.get("icon"), "%"+ shiroResource.getIcon() +"%"));
        }
        if (StringUtils.isNotBlank(shiroResource.getPath())){
            list.add(cb.like(root.get("path"), "%"+ shiroResource.getPath() +"%"));
        }
        if (StringUtils.isNotBlank(shiroResource.getResourceMarking())){
            list.add(cb.like(root.get("resourceMarking"), "%"+ shiroResource.getResourceMarking() +"%"));
        }
        if (StringUtils.isNotBlank(shiroResource.getResourceName())){
            list.add(cb.like(root.get("resourceName"), "%"+ shiroResource.getResourceName() +"%"));
        }
        if (StringUtils.isNotBlank(shiroResource.getResourceUrl())){
            list.add(cb.equal(root.get("resourceUrl"), shiroResource.getResourceUrl()));
        }
        if (StringUtils.isNotBlank(shiroResource.getTitle())){
            list.add(cb.like(root.get("title"), "%"+ shiroResource.getTitle() +"%"));
        }
        if (shiroResource.getSort() != null){
            list.add(cb.equal(root.get("sort"), shiroResource.getSort()));
        }
        if (shiroResource.getType() != null){
            list.add(cb.equal(root.get("type"), shiroResource.getType()));
        }
        if (StringUtils.isNotBlank(shiroResource.getStatus())){
            list.add(cb.equal(root.get("status"), shiroResource.getStatus()));
        }
        if (shiroResource.getCreateTime() != null){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), shiroResource.getCreateTime()));
        }
        if (shiroResource.getUpdateTime() != null){
            list.add(cb.lessThanOrEqualTo(root.get("updateTime"), shiroResource.getUpdateTime()));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }
    public static ShiroResourceExmple createExample(ShiroResource shiroResource){
        return new ShiroResourceExmple(shiroResource);
    }

}
