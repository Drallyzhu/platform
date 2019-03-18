package com.cloudsoft.platform.securityoauth2.domain.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class JwtAuthRequestDTO implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;


    public JwtAuthRequestDTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
