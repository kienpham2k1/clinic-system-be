package org.clinic.authservice.model.sql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.clinic.authservice.constant.SqlDatabaseConstant;
import org.clinic.common_security.security.enums.Role;
import org.clinic.commonserviceweb.audit.sql.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = SqlDatabaseConstant.ROLE_TABLE)
@SQLDelete(sql = "UPDATE tbl_role SET is_deleted = true WHERE role_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
public class RoleEntity extends BaseEntity {
    @OneToMany(mappedBy = "role")
    Set<Authorize> authorizes;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.ROLE_ID)
    private UUID id;
    @Column(name = SqlDatabaseConstant.ROLE_NAME)
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Role name;
}
