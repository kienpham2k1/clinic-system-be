package org.clinic.authservice.model.sql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.clinic.authservice.constant.SqlDatabaseConstant;
import org.clinic.commonserviceweb.audit.sql.BaseEntity;
import org.clinic.commonserviceweb.constant.CommonSqlDatabaseConstant;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = SqlDatabaseConstant.USER_TABLE)
@SQLDelete(sql = "UPDATE tbl_user SET is_deleted = true WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = CommonSqlDatabaseConstant.ID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.USER_NAME)
    @NotNull
    private String username;

    @Column(name = SqlDatabaseConstant.USER_PASSWORD)
    @NotNull
    private String password;

    @Column(name = SqlDatabaseConstant.EMAIL)
    @NotNull
    private String email;
}
