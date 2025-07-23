package org.clinic.commonserviceweb.context;

public class UserContext {
    private final String userId;
    private final String username;
    private final String role;

    public UserContext(String userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}