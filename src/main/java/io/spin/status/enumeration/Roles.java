package io.spin.status.enumeration;

public enum Roles {

    SUPER("ROLE_SUPER"),
    ADMIN("ROLE_ADMIN"),
    AD("ROLE_AD"),
    ;

    final private String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
