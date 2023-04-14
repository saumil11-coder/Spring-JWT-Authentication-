package com.springsecure.test1.model;


import lombok.Data;

@Data
public class TokenRequestBody {
    private String accountNumber;
    private String accountType;
    private String userType;


}
