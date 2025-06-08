package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.model.dto.LoginDTO;
import com.sbfanton.oauth.oauthclient.model.dto.RegisterDTO;
import com.sbfanton.oauth.oauthclient.repository.UserRepository;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getProvider() != null && !user.getProvider().equals(AuthProviderType.LOCAL) && user.getPassword() == null) {
            throw new RuntimeException("Este usuario inició sesión con un proveedor externo. No tiene contraseña configurada.");
        }

        if (user.getPassword() != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );
        }

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .avatarUrl(registerDTO.getAvatarUrl())
                .web(registerDTO.getWeb())
                .provider(AuthProviderType.LOCAL)
                .build();

        userRepository.save(user);

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }

    public AuthResponseDTO saveUserFromProvider(User user) {
        userRepository.save(user);

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }
}
