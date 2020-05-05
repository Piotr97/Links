package com.authorization.repository;

import com.authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r from Role r where r.roleId=?1")
    Role findById(long roleId);
}
