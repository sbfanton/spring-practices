package com.sbfanton.oauth.oauthclient.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.repository.UserRepository;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.service.RedisTokenService;
import com.sbfanton.oauth.oauthclient.utils.constants.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTokenService redisTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String accessToken = jwtService.getAccessTokenFromRequest(request);
        final String refreshToken = jwtService.getRefreshTokenFromRequest(request);
        final String sessionId = jwtService.getSessionIdFromRequest(request);

        String userId = null;
        User user = null;
        boolean isTokenExpired = jwtService.isTokenExpired(accessToken);

        if (isTokenExpired) {
            // Token expirado, tratar de usar refresh token
            if (refreshToken != null) {
                userId = jwtService.getUserIdFromToken(refreshToken);
                user = userRepository.findById(Long.parseLong(userId)).orElse(null);
                boolean isRefreshTokenValid = redisTokenService.validateRefreshToken(userId, refreshToken, sessionId);

                if(isRefreshTokenValid) {
                    String newAccessToken = jwtService.getToken(
                            user,
                            TokenConstants.ACCESS_TOKEN_DURATION);
                            //1000L * 10); // para pruebas
                    ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", newAccessToken)
                            .httpOnly(true)
                            .path("/")
                            .maxAge(Duration.ofSeconds(TokenConstants.ACCESS_TOKEN_DURATION))
                            .sameSite("Lax")
                            .build();
                    response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

                    setAuthentication(user, request);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else {
            userId = jwtService.getUserIdFromToken(refreshToken);
            user = userRepository.findById(Long.parseLong(userId)).orElse(null);
            if(jwtService.isTokenUserValid(accessToken, user)) {
                setAuthentication(user, request);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

