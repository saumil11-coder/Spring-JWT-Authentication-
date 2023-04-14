package com.springsecure.test1.model;

import lombok.Data;

@Data
public class JwtToken {
    private String accountType;
    private String jwtToken;
}
