package org.clinic.doctorservice.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.doctorservice.constant.SqlDatabaseConstant;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tbl_doctor SET is_deleted = true WHERE doctor_id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@Table(name = SqlDatabaseConstant.DOCTOR)
@Builder
public class DoctorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.DOCTOR_ID)
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private LocalDate birthDate;
    private String country;
    private String city;
    private String state;
    private String zip;
    private String profileImage;
}
