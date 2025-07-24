package org.clinic.commonserviceweb.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@AllArgsConstructor
public enum Role {
    ANONYMOUS(Collections.emptySet()),
    PATIENT(Set.of(
            Permission.PATIENT_READ,
            Permission.PATIENT_CREATE,
            Permission.PATIENT_UPDATE,
            Permission.PATIENT_DELETE
    )),
    DOCTOR(Set.of(
            Permission.DOCTOR_READ,
            Permission.DOCTOR_CREATE,
            Permission.DOCTOR_UPDATE,
            Permission.DOCTOR_DELETE
    )),
    ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE
    ));
    @Getter
    private final Set<Permission> permissions;
}
