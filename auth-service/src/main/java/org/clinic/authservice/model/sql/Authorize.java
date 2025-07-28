package org.clinic.authservice.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.authservice.constant.SqlDatabaseConstant;
import org.clinic.commonserviceweb.audit.sql.BaseEntity;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = SqlDatabaseConstant.AUTHORIZE)
public class Authorize extends BaseEntity{
    @EmbeddedId
    private AuthorizeId authorizeId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = SqlDatabaseConstant.USER_ID)
    private UserEntity user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = SqlDatabaseConstant.ROLE_ID)
    private RoleEntity role;
}
