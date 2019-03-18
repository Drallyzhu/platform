package com.cloudsoft.platform.shiro.repository;

import com.cloudsoft.platform.shiro.model.ShiroRole;
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
public interface ShiroRoleRepository extends JpaRepository<ShiroRole, String>, JpaSpecificationExecutor<ShiroRole> {

    Optional<ShiroRole> findById(String roleId);


//    /**
//     * 获取角色列表
//     * @return
//     */
//    @Query(value = "select * from shiro_role order by id asc",nativeQuery = true)
//    List<ShiroRole> selectAll();

    /**
     * 根据用户id获取用户绑定的角色ids
     * @param userId
     * @return
     */
    @Query(value = "SELECT role_id FROM shiro_user_role WHERE user_id =:userId ORDER BY role_id ASC",nativeQuery = true)
    List<String> selectIdsByUserId(@Param(value = "userId") String userId);

    /**
     * 根据状态获取角色列表
     * @param status
     * @return
     */
    @Query(value = "select * from shiro_role where status =:status order by id asc",nativeQuery = true)
    List<ShiroRole> selectAllAndStatus(@Param(value = "status") String status);




}
