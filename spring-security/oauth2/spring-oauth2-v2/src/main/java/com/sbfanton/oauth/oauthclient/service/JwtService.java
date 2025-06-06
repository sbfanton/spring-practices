package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.UserDTO;
import com.sbfanton.oauth.oauthclient.utils.RandomKeyGen;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey;

    @PostConstruct
    public void generateSKey() {
        secretKey = RandomKeyGen.generateRandomKey();
    }


    public String getToken(User user, Boolean isTemporary) {
        Map<String, Object> extraClaims = generateExtraClaimsMap(user);
        return generateToken(extraClaims, user, isTemporary);
    }


    private Map<String, Object> generateExtraClaimsMap(User user) {
        Map <String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getUsername());
        extraClaims.put("avatar", user.getAvatarUrl());
        extraClaims.put("web", user.getWeb());
        return extraClaims;
    }


    private String generateToken(Map<String, Object> extraClaims, User user, Boolean isTemporary) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis())) //fecha de creacion
                .expiration(
                                isTemporary ?
                                new Date(System.currentTimeMillis() + 1000*120) :
                                new Date(System.currentTimeMillis() + 1000*60*24)) //fecha de expiracion
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        //String username = getUsernameFromToken(token);
        return (/*username.equals(userDetails.getUsername()) &&*/ !isTokenExpired(token));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeaderPreffix = "Bearer ";
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(authHeaderPreffix)) {
            return authHeader.substring(authHeaderPreffix.length());
        }
        return null;
    }

    public User getUserFromToken(String token) {
        String username = getClaim(token, claims -> claims.get("userId", String.class));
        String avatar = getClaim(token, claims -> claims.get("avatar", String.class));
        String web = getClaim(token, claims -> claims.get("web", String.class));

        return User.builder()
                .username(username)
                .avatarUrl(avatar)
                .web(web)
                .build();
    }
}
