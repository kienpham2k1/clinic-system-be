package org.clinic.patientservice.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.commonserviceweb.entity.sql.BaseEntity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private LocalDate birthDate;
//    @Nullable
//    private GenderEnum gender;
//    @Nullable
//    private BloodGroupEnum bloodGroupEnum;
    private String country;
    private String city;
    private String state;
    private String zip;
    private String profileImage;
}
