package org.clinic.authservice.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.clinic.authservice.constant.SqlDatabaseConstant;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizeId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = SqlDatabaseConstant.USER_ID)
    private UUID userId;
    @Column(name = SqlDatabaseConstant.ROLE_ID)
    private UUID roleId;
}
