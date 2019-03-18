package com.cloudsoft.platform.securityoauth2.domain;

import com.cloudsoft.platform.securityoauth2.domain.vo.RoleVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RoleExample implements Specification<Role> {

    private static final long serialVersionUID = -7898234982313933159L;
    private RoleVO roleVo;

    private RoleExample(RoleVO roleVo) {
        this.roleVo = roleVo;
    }


    @Override
    public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();

        if (StringUtils.isNotBlank(roleVo.getName())) {
            list.add(criteriaBuilder.like(root.get("name"), "%" + roleVo.getName() + "%"));
        }
        if (StringUtils.isNotBlank(roleVo.getStatus())) {
            list.add(criteriaBuilder.equal(root.get("status"), roleVo.getStatus()));
        }
        if (StringUtils.isNotBlank(roleVo.getShowAdmin()) && "0".equals(roleVo.getShowAdmin())) {
            list.add(criteriaBuilder.notEqual(root.get("id"), 0));
        }

        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }

    public static RoleExample createExample(RoleVO roleVo) {
        return new RoleExample(roleVo);
    }

}
