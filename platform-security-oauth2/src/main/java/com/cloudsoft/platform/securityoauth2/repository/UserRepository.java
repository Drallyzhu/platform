package com.cloudsoft.platform.securityoauth2.repository;

import com.cloudsoft.platform.securityoauth2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByCodeAndPwd(String code, String pwd);

    User findByCode(String code);

    User findByMobileAndPwd(String mobile, String pwd);

    List<User> findByRole(String role);

    User findByAppId(String appId);

    User findByMobile(String mobile);

    Optional<User> findByInvitationCodeAndExamine(String invitationCode, String examine);

    Optional<List<User>> findByInvitationCodeIn(Collection<String> invitationCodeS);

    @Override
    Optional<User> findById(String id);

    Optional<User> findByMobileAndIsDelete(String mobile, String isDelete);

    Long countByExamineAndRoleAndIsDelete(String examine, String role, String isDelete);

    Long countByStatusAndRoleAndIsDelete(String status, String role, String isDelete);

    Long countByRoleAndIsDelete(String role, String isDelete);

    @Query(value = "select count(1) from user  where to_days(user.create_time) = to_days(now()) and role='ROLE_CUSTOMER' and is_delete='0'", nativeQuery = true)
    Long getTodayCreateUser();

    @Query(value = "select count(1) from user  where to_days(user.create_time) = to_days(now()) and examine='2' and role='ROLE_CUSTOMER' and is_delete='0'", nativeQuery = true)
    Long getTodayCreateAuthenticationUser();

    @Modifying
    @Query(value = "update user set role_name=:roleName where role_id=:roleId", nativeQuery = true)
    int updateByRoleId(@Param("roleName") String roleName, @Param("roleId") String roleId);
}
