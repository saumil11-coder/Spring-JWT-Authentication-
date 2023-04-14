package com.springsecure.test1.service;

import com.springsecure.test1.model.JwtToken;
import com.springsecure.test1.model.TokenRequestBody;
import com.springsecure.test1.util.ApplicationConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import org.springframework.http.HttpHeaders;

import javax.crypto.spec.SecretKeySpec;

@Service
public class TokenService {
    @Autowired
    private ApplicationConfig applicationConfig;
    public JwtToken generateToken(TokenRequestBody body, HttpHeaders headers) {

        String jwtToken = Jwts.builder()
                .claim("accountNumber", body.getAccountNumber())
                .claim("accountType", body.getAccountType())
                .claim("userType", body.getUserType())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .compact();

        JwtToken jwtTokenObject = new JwtToken();
        jwtTokenObject.setJwtToken(jwtToken);
        jwtTokenObject.setAccountType(headers.getFirst("accountType"));
        return jwtTokenObject;
    }


    public JwtToken generateTokenUsingSecretKey(TokenRequestBody body, HttpHeaders headers) {

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(applicationConfig.getSecretKey()),
                SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = Jwts.builder()
                .claim("accountNumber", body.getAccountNumber())
                .claim("accountType", body.getAccountType())
                .claim("userType", body.getUserType())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, hmacKey)
                .compact();

        JwtToken jwtTokenObject = new JwtToken();
        jwtTokenObject.setJwtToken(jwtToken);
        jwtTokenObject.setAccountType(headers.getFirst("accountType"));
        return jwtTokenObject;
    }

    public Claims parseJwt(String jwtToken) {
        Claims jwt = Jwts.parser()
                .setSigningKey(applicationConfig.getSecretKey())
                .parseClaimsJws(jwtToken)
                .getBody();
        return jwt;
    }
}
