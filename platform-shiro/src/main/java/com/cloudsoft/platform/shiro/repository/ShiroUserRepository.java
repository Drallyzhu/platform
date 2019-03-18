package com.cloudsoft.platform.shiro.repository;

import com.cloudsoft.platform.shiro.model.ShiroUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author zhujianrong
 * @date 2018-11-06 16:12
 */
@Repository
public interface ShiroUserRepository extends JpaRepository<ShiroUser, String>, JpaSpecificationExecutor<ShiroUser> {

    void deleteById(String userId);

    Optional<ShiroUser> findById(String s);

    Optional<ShiroUser> findByLoginName(String loginName);

    @Query(value = "select count(*) from shiro_user where id =:userId and login_name=:loginName",nativeQuery = true)
    long checkLoginName(@Param(value = "userId") String userId, @Param(value = "loginName") String loginName);

    @Query(value = "select count(*) from shiro_user where  login_name=:loginName",nativeQuery = true)
    long checkLoginNames(@Param(value = "loginName") String loginName);

}
