package com.cloudsoft.platform.shiro.repository;

import com.cloudsoft.platform.shiro.model.ShiroUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author zhujianrong
 * @date 2018-11-06 16:12
 */
@Repository
public interface ShiroUserRoleRepository extends JpaRepository<ShiroUserRole, String>, JpaSpecificationExecutor<ShiroUserRole> {



    @Modifying
    @Query(value = "delete from shiro_user_role where user_id =:userId",nativeQuery = true)
    void deleteByUserID(@Param(value = "userId") String userId);



}
