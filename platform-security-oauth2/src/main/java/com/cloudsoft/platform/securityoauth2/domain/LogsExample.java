package com.cloudsoft.platform.securityoauth2.domain;


import com.cloudsoft.common.util.DateUtils;
import com.cloudsoft.platform.securityoauth2.domain.vo.LogsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pc
 * @date 2018/10/16 19:55
 */
public class LogsExample implements Specification<Logs> {

    private static final long serialVersionUID = 2985221257550367584L;
    
    private LogsVo logsVo;

    private LogsExample(LogsVo logsVo){
        this.logsVo = logsVo;
    }

    @Override
    public Predicate toPredicate(Root<Logs> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();

        if (StringUtils.isNotBlank(logsVo.getOperatorId())){
            list.add(cb.equal(root.get("operatorId"),  logsVo.getOperatorId() ));
        }

        if (StringUtils.isNotBlank(logsVo.getOperatorName())){
            list.add(cb.equal(root.get("operatorName"),  logsVo.getOperatorName() ));
        }

        if (StringUtils.isNotBlank(logsVo.getUserId())){
            list.add(cb.equal(root.get("userId"),  logsVo.getUserId() ));
        }

        if (StringUtils.isNotBlank(logsVo.getUsername())){
            list.add(cb.equal(root.get("username"),  logsVo.getUsername() ));
        }

        if (StringUtils.isNotBlank(logsVo.getActionName())){
            list.add(cb.like(root.get("actionName"), "%" + logsVo.getActionName() + "%"));
        }

        if(null != logsVo.getBeginDate()){
            list.add(cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.beginOfDay(logsVo.getBeginDate())));
        }

        if(null != logsVo.getEndDate()){
            list.add(cb.lessThanOrEqualTo(root.get("createTime"), DateUtils.endOfDay(logsVo.getEndDate())));
        }

        return query.where(list.toArray(new Predicate[list.size()])).getRestriction();
    }

    public static LogsExample createExample(LogsVo logsVo){
        return new LogsExample(logsVo);
    }
}
