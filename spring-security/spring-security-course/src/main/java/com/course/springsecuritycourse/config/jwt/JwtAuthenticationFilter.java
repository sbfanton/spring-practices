package com.course.springsecuritycourse.config.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    /*
     * Utilizamos OncePerRequestFilter para asegurarnos que este filtro se ejecute solo una vez 
     * por cada peticion http y no varias veces por error.
     * 
     * Por otro lado, el siguiente metodo es el que va a manejar todo lo relacionado a los filtros 
     * para validar el JWT
     * 
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String token = getTokenFromRequest(request);

        if(token == null) {
            /* SI el token es nulo, no se hara validacion de JWT y continuara cadena de filtros ya sea para registrar 
             * un nuevo usuario o para autenticarlo (y luego de eso se le enviara eel jwt en la respuesta)
             */
            filterChain.doFilter(request, response); 
            return;
        }

        filterChain.doFilter(request, response);
    }

    /* El token se obtiene del encabezado del request */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeaderPreffix = "Bearer ";
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(authHeader)) {
            return authHeader.substring(authHeader.length());
        }
        return null;
    }

}
