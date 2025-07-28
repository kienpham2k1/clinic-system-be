package org.clinic.authservice.repository;

import org.clinic.authservice.model.sql.Authorize;
import org.clinic.authservice.model.sql.AuthorizeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizeRepository extends JpaRepository<Authorize, AuthorizeId> {
}
