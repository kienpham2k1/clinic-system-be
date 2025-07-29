package org.clinic.doctor_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.doctor_service.constant.SqlDatabaseConstant;
import org.clinic.doctor_service.enums.SpecializationStatus;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tbl_specialization SET is_deleted = true WHERE doctor_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@Table(name = SqlDatabaseConstant.SPECIALIZATION)
@Builder
public class SpecializationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.SPECIALIZATION_ID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.SPECIALIZATION_NAME)
    private String name;

    @Column(name = SqlDatabaseConstant.SPECIALIZATION_NO_OF_DOCTOR)
    private Long noOfDoctor;

    @Column(name = SqlDatabaseConstant.SPECIALIZATION_STATUS)
    @Enumerated(EnumType.ORDINAL)
    private SpecializationStatus status;
}
