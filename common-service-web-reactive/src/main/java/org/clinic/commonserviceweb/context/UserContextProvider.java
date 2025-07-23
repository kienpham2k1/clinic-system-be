package org.clinic.commonserviceweb.context;

import java.util.Optional;

public interface UserContextProvider {

    Optional<UserContext> getCurrentUser();

    String getUserId();

    String getUsername();

    String getRole();
}