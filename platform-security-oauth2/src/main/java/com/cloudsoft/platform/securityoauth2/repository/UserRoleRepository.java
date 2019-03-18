package com.cloudsoft.platform.securityoauth2.repository;

import com.cloudsoft.platform.securityoauth2.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, String>, JpaSpecificationExecutor<UserRole> {
    Optional<List<UserRole>> findByRoleId(String roleId);

    @Modifying
    @Query(value = "delete from user_role where user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") String userId);

}
