package org.example.commonservice.context;

import java.util.Optional;

public interface UserContextProvider {

    Optional<UserContext> getCurrentUser();

    String getUserId();

    String getUsername();

    String getRole();
}