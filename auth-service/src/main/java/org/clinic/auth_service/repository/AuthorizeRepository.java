package org.clinic.auth_service.repository;

import org.clinic.auth_service.model.sql.Authorize;
import org.clinic.auth_service.model.sql.AuthorizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizeRepository extends JpaRepository<Authorize, AuthorizeId> {
}
