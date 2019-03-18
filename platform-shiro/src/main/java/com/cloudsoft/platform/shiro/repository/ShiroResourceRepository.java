package com.cloudsoft.platform.shiro.repository;

import com.cloudsoft.platform.shiro.model.ShiroResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface ShiroResourceRepository extends JpaRepository<ShiroResource, String>, JpaSpecificationExecutor<ShiroResource> {

    Optional<ShiroResource> findById(String resourceId);

    List<ShiroResource> findAllByStatus(String status, Sort sort);


    @Query(value="select b.* from \n" +
                    "(SELECT b.* from \n" +
                    "(SELECT b.* from \n" +
                    "(SELECT b.* from shiro_user a JOIN shiro_user_role b on a.id = b.user_id \n" +
                        "where a.id =:userId AND a.status =:userStatus \n" +
                    ") \n" +
                    "a JOIN shiro_role b \n" +
                    "on a.role_id = b.id) a JOIN shiro_role_resource b \n" +
                    "on a.id = b.role_id) a JOIN shiro_resource b \n" +
                    "on a.resource_id = b.id ORDER BY b.`sort` ASC",
                    nativeQuery=true)
    List<ShiroResource> getUserResAll(@Param(value = "userId") String userId, @Param(value = "userStatus") String userStatus);


    @Query(value = "select * from shiro_resource a,(\n" +
            "select a.resource_id from shiro_role_resource a where a.role_id=:roleId) b\n" +
            "where a.id =b.resource_id",nativeQuery = true)
    List<ShiroResource> getResourcesByRole(@Param(value = "roleId") String roleId);



}
