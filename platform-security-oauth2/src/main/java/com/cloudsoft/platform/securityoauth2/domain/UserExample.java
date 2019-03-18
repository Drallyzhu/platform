package com.cloudsoft.platform.securityoauth2.domain;

import com.cloudsoft.common.util.DateUtils;
import com.cloudsoft.platform.securityoauth2.domain.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserExample implements Specification<User> {

    private static final long serialVersionUID = 2105974256283533054L;
    private UserVO userVo;

    private UserExample(UserVO userVo) {
        this.userVo = userVo;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();


        if (StringUtils.isNotBlank(userVo.getName())) {
            list.add(cb.like(root.get("name"), "%" + userVo.getName() + "%"));
        }
        if (StringUtils.isNotBlank(userVo.getMobile())) {
            list.add(cb.like(root.get("mobile"), "%" + userVo.getMobile() + "%"));
        }
        if (StringUtils.isNotBlank(userVo.getStatus())) {
            list.add(cb.equal(root.get("status"), userVo.getStatus()));
        }
        if (StringUtils.isNotBlank(userVo.getExamine())) {
            list.add(cb.equal(root.get("examine"), userVo.getExamine()));
        }
        if (StringUtils.isNotBlank(userVo.getRole())) {
            list.add(cb.equal(root.get("role"), userVo.getRole()));
        }
        if (null != userVo.getIsDelete()) {
            list.add(cb.equal(root.get("isDelete"), userVo.getIsDelete()));
        }
        if (null != userVo.getParentName()) {
            list.add(cb.like(root.get("parentName"), "%" + userVo.getParentName() + "%"));
        }
        if (null != userVo.getPollCode()) {
            list.add(cb.equal(root.get("pollCode"), userVo.getPollCode()));
        }
        if (null != userVo.getBeginDate()) {
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.beginOfDay(userVo.getBeginDate())));
        }
        if (null != userVo.getEndDate()) {
            list.add(cb.lessThanOrEqualTo(root.get("createTime"), DateUtils.endOfDay(userVo.getEndDate())));
        }
        if (null != userVo.getExamineBeginDate()) {
            list.add(cb.greaterThanOrEqualTo(root.get("examineTime"), DateUtils.beginOfDay(userVo.getExamineBeginDate())));
        }

        if (null != userVo.getExamineEndDate()) {
            list.add(cb.lessThanOrEqualTo(root.get("examineTime"), DateUtils.endOfDay(userVo.getExamineEndDate())));
        }
        return criteriaQuery.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }

    public static UserExample createExample(UserVO userVo) {
        return new UserExample(userVo);
    }

}
