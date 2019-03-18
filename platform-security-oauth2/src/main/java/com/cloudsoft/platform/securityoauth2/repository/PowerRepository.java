package com.cloudsoft.platform.securityoauth2.repository;

import com.cloudsoft.platform.securityoauth2.domain.Power;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PowerRepository extends JpaRepository<Power,String>,JpaSpecificationExecutor<Power> {

    @Query(value = "SELECT p.* FROM power AS p INNER JOIN role_power AS rp ON p.id = rp.power_id WHERE rp.role_id = :roleId", nativeQuery = true)
    List<Power> findByRole(@Param("roleId") String roleId);

    @Query(value = "SELECT p.* FROM power AS p INNER JOIN role_power AS rp ON p.id = rp.power_id WHERE p.type = :type and rp.role_id = :roleId", nativeQuery = true)
    List<Power> findByRoleAndType(@Param("roleId") String roleId, @Param("type") String type);

}
