package org.clinic.authservice.model.sql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.clinic.authservice.constant.SqlDatabaseConstant;
import org.clinic.common_service_web.audit.sql.BaseEntity;
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
@Table(name = SqlDatabaseConstant.USER_TABLE)
@SQLDelete(sql = "UPDATE tbl_user SET is_deleted = true WHERE user_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
public class UserEntity extends BaseEntity {
    @OneToMany(mappedBy = "user")
    Set<Authorize> authorizes;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.USER_ID)
    private UUID id;
    @Column(name = SqlDatabaseConstant.USER_NAME)
    @NotNull
    private String username;
    @Column(name = SqlDatabaseConstant.USER_PASSWORD)
    @NotNull
    private String password;
    @Column(name = SqlDatabaseConstant.USER_EMAIL)
    @NotNull
    private String email;
}
