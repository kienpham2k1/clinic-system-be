package org.clinic.authservice.repository;

import org.clinic.authservice.model.sql.RoleEntity;
import org.clinic.common_security.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(Role name);
}
