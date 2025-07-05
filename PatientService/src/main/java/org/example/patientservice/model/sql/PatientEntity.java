package org.example.patientservice.model.sql;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
}
