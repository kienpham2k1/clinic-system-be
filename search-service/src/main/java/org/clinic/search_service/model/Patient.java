package org.clinic.search_service.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Document(indexName = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Patient {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private Long birthDate;
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
