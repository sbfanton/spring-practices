package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.utils.RandomKeyGen;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey;

    @PostConstruct
    public void generateSKey() {
        secretKey = RandomKeyGen.generateRandomKey();
    }


    public String getToken(User user, Long seconds) {
        Map<String, Object> extraClaims = generateExtraClaimsMap(user);
        return generateToken(extraClaims, user, seconds);
    }


    private Map<String, Object> generateExtraClaimsMap(User user) {
        Map <String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", user.getUsername());
        extraClaims.put("web", user.getWeb());
        return extraClaims;
    }


    private String generateToken(Map<String, Object> extraClaims, User user, Long seconds) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis())) //fecha de creacion
                .expiration(new Date(System.currentTimeMillis() + seconds))
                .id(UUID.randomUUID().toString())
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserIdFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token, User user) {
        String userId = getUserIdFromToken(token);
        return (userId.equals(String.valueOf(user.getId())) && !isTokenExpired(token));
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

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public User getUserFromToken(String token) {
        String userId = getUserIdFromToken(token);
        String username = getClaim(token, claims -> claims.get("username", String.class));
        String web = getClaim(token, claims -> claims.get("web", String.class));

        return User.builder()
                .id(Long.parseLong(userId))
                .username(username)
                .web(web)
                .build();
    }

    public Map<String, ResponseCookie> generateTokenCookies(AuthResponseDTO authResponseDTO) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", authResponseDTO.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax")
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", authResponseDTO.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax")
                .build();

        Map<String, ResponseCookie> cookiesMap = new HashMap<>();
        cookiesMap.put("access_token", accessTokenCookie);
        cookiesMap.put("refresh_token", refreshTokenCookie);
        return cookiesMap;
    }

    public HttpServletResponse invalidateTokenCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return response;
    }
}
