package com.course.springsecuritycourse.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.course.springsecuritycourse.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

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

    private static final String SECRET_KEY = "901f47cae3b3817661ad2cceea486d0dd36a831ff4370a994996896d994c8ca166d0d269a42e36acfcd7fc11254f7c32b610e2d428ac67b7cce1e592fbb373ca93dc4ea16e28648b42f62505a32d11c74c0ed7f6eb0fcdb13af7d6dff590b1f66001539588e9f6b20a6e70e6ac9f815ec4f19d2565f9ca64ad2902761f5e5fee";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                   .claims(extraClaims)
                   .subject(user.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis())) //fecha de creacion
                   .expiration(new Date(System.currentTimeMillis() + 1000*60*24)) //fecha de expiracion
                   .signWith(getKey(), SIG.HS256)
                   .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
