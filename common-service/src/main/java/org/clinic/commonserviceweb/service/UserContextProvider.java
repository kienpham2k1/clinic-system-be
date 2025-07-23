package org.clinic.commonserviceweb.service;

import org.clinic.commonserviceweb.dto.UserContext;

import java.util.Optional;

public interface UserContextProvider {

    Optional<UserContext> getCurrentUser();

    String getUserId();

    String getUsername();

    String getRole();
}