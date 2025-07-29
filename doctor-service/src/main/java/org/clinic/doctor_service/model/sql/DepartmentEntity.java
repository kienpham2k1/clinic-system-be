package org.clinic.doctor_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.doctor_service.constant.SqlDatabaseConstant;
import org.clinic.doctor_service.enums.DepartmentStatus;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tbl_department SET is_deleted = true WHERE doctor_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@Table(name = SqlDatabaseConstant.DEPARTMENT)
@Builder
public class DepartmentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.DEPARTMENT_ID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.DEPARTMENT_NAME)
    private String name;

    @Column(name = SqlDatabaseConstant.DEPARTMENT_NO_OF_DOCTOR)
    private Long noOfDoctor;

    @Column(name = SqlDatabaseConstant.DEPARTMENT_STATUS)
    @Enumerated(EnumType.ORDINAL)
    private DepartmentStatus status;
}
