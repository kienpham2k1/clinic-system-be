package org.clinic.auth_service.repository;

import org.clinic.auth_service.model.sql.RoleEntity;
import org.clinic.common_security.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(Role name);
}
