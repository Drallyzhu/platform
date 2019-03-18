package com.cloudsoft.platform.securityoauth2.repository;

import com.cloudsoft.platform.securityoauth2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    @Query(value = "SELECT r.* FROM role AS r INNER JOIN user_role AS ur ON r.id = ur.role_id WHERE ur.user_id = :userId", nativeQuery = true)
    List<Role> findByUserId(@Param("userId") String userId);


    Optional<Role> findByName(String name);

    Optional<Role> findByCode(String code);
}
