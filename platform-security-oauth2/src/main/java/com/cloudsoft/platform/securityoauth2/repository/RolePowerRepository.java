package com.cloudsoft.platform.securityoauth2.repository;

import com.cloudsoft.platform.securityoauth2.domain.RolePower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolePowerRepository extends JpaRepository<RolePower, String>, JpaSpecificationExecutor<RolePower> {

    Optional<List<RolePower>> findByRoleId(String roleId);

    @Modifying
    @Query(value = "DELETE from role_power where role_id=:roleId", nativeQuery = true)
    void deleteByRoleId(@Param("roleId") String roleId);

    Optional<List<RolePower>> findByPowerId(String powerId);

}
