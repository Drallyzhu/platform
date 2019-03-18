package com.cloudsoft.platform.shiro.repository;

import com.cloudsoft.platform.shiro.model.ShiroResource;
import com.cloudsoft.platform.shiro.model.ShiroRoleResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author zhujianrong
 * @date 2018-11-06 16:12
 */
@Repository
public interface ShiroRoleResourceRepository extends JpaRepository<ShiroRoleResource, String>, JpaSpecificationExecutor<ShiroRoleResource> {

    Optional<ShiroRoleResource> findById(String resourceId);

    List<ShiroResource> findAllByStatus(String status, Sort sort);

    @Modifying
    @Query(value = "delete from shiro_role_resource where role_id =:roleId",nativeQuery = true)
    void deleteByRoleId(@Param(value = "roleId") String roleId);


}
