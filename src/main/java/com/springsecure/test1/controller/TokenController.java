package com.springsecure.test1.controller;

import com.springsecure.test1.model.JwtToken;
import com.springsecure.test1.model.TokenRequestBody;
import com.springsecure.test1.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;
    @PostMapping("/generate/jwt/token")
    public ResponseEntity<JwtToken> generateJwtToken(
            @RequestBody TokenRequestBody body,
            @RequestHeader(value = "apiversion", required=true) String apiVersion,
            @RequestHeader(value = "accountType", required=true) String accountType,
            @RequestHeader(value = "messageId", required = true) String messageIds
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("apiversion", apiVersion);
        headers.add("accountType", accountType);
        headers.add("messageId", messageIds);
        return new ResponseEntity<JwtToken>(tokenService.generateTokenUsingSecretKey(body, headers), HttpStatus.OK);
    }
    @PostMapping("/validate/jwt/token")
    public Claims jwtTokenValidate(
            @RequestHeader(value = "token", required=true) String jwtToken,
            @RequestHeader(value = "apiversion", required=true) String apiVersion,
            @RequestHeader(value = "accountType", required=true) String accountType,
            @RequestHeader(value = "messageId", required = true) String messageIds
    ) {
        return tokenService.parseJwt(jwtToken);
    }
}
