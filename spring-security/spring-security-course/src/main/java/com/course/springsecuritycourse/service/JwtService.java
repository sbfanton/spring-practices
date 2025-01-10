package com.course.springsecuritycourse.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.course.springsecuritycourse.entity.User;
import com.course.springsecuritycourse.util.RandomKeyGen;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/*
 * Los claims en un JSON Web Token (JWT) son declaraciones sobre una entidad (generalmente, el usuario) y metadatos adicionales. Los claims son la parte central del JWT y se utilizan para transmitir información entre las partes de manera segura y confiable.

Tipos de Claims
Los claims en un JWT se pueden clasificar en tres categorías:

Registered Claims (Claims Registrados):

Son un conjunto de claims predefinidos que no son obligatorios, pero se recomiendan para proporcionar interoperabilidad. Algunos ejemplos incluyen:
- iss (Issuer): Identifica al emisor del token.
- sub (Subject): Identifica al sujeto del token (generalmente, el usuario).
- aud (Audience): Identifica a los destinatarios del token.
- exp (Expiration Time): Indica la fecha y hora de expiración del token.
- nbf (Not Before): Indica que el token no debe ser aceptado antes de una fecha y hora específicas.
- iat (Issued At): Indica la fecha y hora en que se emitió el token.

Los claims se encuentran en el payload (carga útil) del jwt
 */

@Service
public class JwtService {

    private String secretKey;

    @PostConstruct
    public void generateSKey() {
        secretKey = RandomKeyGen.generateRandomKey();
    }

    public String getToken(User user) {
        Map<String, Object> extraClaims = generateExtraClaimsMap(user);
        return generateToken(extraClaims, user);
    }

    private Map<String, Object> generateExtraClaimsMap(User user) {
        Map <String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getUsername());
        extraClaims.put("firstName", user.getFirstName());
        extraClaims.put("lastName", user.getLastName());
        extraClaims.put("country", user.getCountry());
        return extraClaims;
    }

    private String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                   .claims(extraClaims)
                   .subject(user.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis())) //fecha de creacion
                   .expiration(new Date(System.currentTimeMillis() + 1000*60*24)) //fecha de expiracion
                   .signWith(getKey(), SIG.HS256)
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
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
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
}