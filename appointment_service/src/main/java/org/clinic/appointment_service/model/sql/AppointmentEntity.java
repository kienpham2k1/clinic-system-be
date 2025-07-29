package org.clinic.appointment_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.appointment_service.constant.SqlDatabaseConstant;
import org.clinic.appointment_service.enums.Mode;
import org.clinic.appointment_service.enums.Status;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tbl_doctor SET is_deleted = true WHERE doctor_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@Table(name = SqlDatabaseConstant.APPOINTMENT)
@Builder
public class AppointmentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.APPOINTMENT_ID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_FK_DOCTOR_ID)
    private UUID doctorId;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_FK_PATIENT_ID)
    private UUID patientId;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_FK_DEPARTMENT_ID)
    private UUID departmentId;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_MODE)
    @Enumerated(EnumType.ORDINAL)
    private Mode mode;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_DATE)
    private LocalDate date;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_TIME)
    private LocalTime time;

    @Column(name = SqlDatabaseConstant.APPOINTMENT_STATUS)
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
