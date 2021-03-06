package com.bakery.server.config;

import com.bakery.server.exception.UnauthorizedException;
import com.bakery.server.model.response.UserResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    private Gson gson;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.expired}")
    private Long jwtExpired;

    public String generateAccessToken(UserDetails user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return Jwts.builder()
                .setId(String.valueOf(userResponse.getId()))
                .setSubject(gson.toJson(userResponse, UserResponse.class))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpired)) // 1 week
                .signWith(getKey(), signatureAlgorithm)
                .compact();
    }

    public String refreshToken(String token) {
        Claims claims;
        try {
            claims = getClaims(token);
        } catch (ExpiredJwtException ex) {
            claims = ex.getClaims();
        }

        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setIssuedAt(claims.getIssuedAt())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpired))
                .signWith(getKey(), signatureAlgorithm)
                .compact();
    }

    public JwtParser getJwtParser() {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build();
    }

    public Claims getClaims(String token) {
        return getJwtParser().parseClaimsJws(token).getBody();
    }

    public UserResponse getUser(String token) {
        Claims claims = getClaims(token);
        return new Gson().fromJson(claims.getSubject(), UserResponse.class);
    }

    public Long getUserId(String token) {
        return Long.valueOf(getClaims(token).getId());
    }

    public String getUsername(String token) {
        return getUser(token).getUsername();
    }

    public Date getExpirationDate(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public boolean isExpired(String token) {
        Date expireDate = getExpirationDate(token);
        return expireDate.compareTo(new Date()) < 0;
    }

    public boolean validate(String token) {
        try {
            getJwtParser().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            log.error("(validate) ex: {}", ex.getMessage(), ex);
            throw UnauthorizedException.build();
        }
    }

    private Key getKey() {
        byte[] secret = DatatypeConverter.parseBase64Binary(jwtSecret);
        return new SecretKeySpec(secret, signatureAlgorithm.getJcaName());
    }

}