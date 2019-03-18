package com.cloudsoft.security.enumeration;

/**
 * @author zhujianrong
 */
public enum AccessLevel {

    PUBLIC("public"), INTERNAL("sys"), ADMIN("public"), USER("user");

    private String level;

    AccessLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
