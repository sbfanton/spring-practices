package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.*;
import com.sbfanton.oauth.oauthclient.model.mapper.UserMapper;
import com.sbfanton.oauth.oauthclient.repository.UserRepository;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import com.sbfanton.oauth.oauthclient.utils.constants.CodeTypes;
import com.sbfanton.oauth.oauthclient.utils.constants.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Value("${uploads.path}")
    private String uploadsPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public AuthResponseDTO login(LoginDTO loginDTO) throws Exception {
        User user = checkAuthUser(loginDTO.getUsername(), loginDTO.getPassword());
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }

    public AuthResponseDTO register(RegisterDTO registerDTO) throws ServiceException {

        User userExists = getUserByUsername(registerDTO.getUsername())
                .orElse(null);

        if(userExists != null)
            throw new ServiceException("El nombre de usuario ya existe");

        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .avatarUrl(registerDTO.getAvatarUrl())
                .web(registerDTO.getWeb())
                .provider(AuthProviderType.LOCAL)
                .build();

        saveUser(user);

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }

    public UserDTO getUserDTO(String username) throws Exception {
        User user = getUserByUsername(username)
                .orElse(null);

        if(user == null)
            throw new ServiceException("Usuario no encontrado");

        return userMapper.userToUserDTO(user);
    }

    public AuthResponseDTO saveUserFromProvider(User user) {
        User userExists = getUserByUsername(user.getUsername())
                .orElse(null);
        if(userExists == null)
            saveUser(user);

        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user, false))
                .build();
    }

    public StatusDTO changeUser(String username, UserDTO userDTO) throws Exception {
        User user = getUserByUsername(username)
                .orElse(null);

        if(user == null)
            throw new ServiceException("El usuario al que se le quiere editar atributos no existe.");

        user.setWeb(userDTO.getWeb());
        user.setEmail(userDTO.getEmail());
        saveUser(user);

        return StatusDTO.builder()
                .message("Usuario editado correctamente")
                .code(CodeTypes.SUCCESS.name())
                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                .build();
    }

    public StatusDTO changePassword(String username, PasswordEditDTO passwordEditDTO) throws Exception {
        User user = checkAuthUser(username, passwordEditDTO.getCurrentPassword());
        user.setPassword(passwordEditDTO.getNewPassword());
        saveUser(user);
        return StatusDTO.builder()
                .message("Contraseña cambiada correctamente.")
                .code(CodeTypes.SUCCESS.name())
                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                .build();
    }

    public StatusDTO changeAvatar(String username, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new ServiceException("Archivo vacío.");
        }

        String contentType = file.getContentType();
        List<String> allowedTypes = List.of("image/jpeg", "image/jpg", "image/png");
        Boolean isAllowed = allowedTypes.contains(contentType);

        if(!isAllowed) {
            throw new ServiceException(
                    "Tipo de archivo no soportado para el avatar.",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        File directory = new File(uploadsPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String filename = username + "_avatar" + extension;
        Path filepath = Paths.get(uploadsPath, filename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        User user = getUserByUsername(username)
                .orElse(null);
        if (user == null)
            throw new ServiceException("El usuario al que se le quiere cambiar el avatar no existe");

        user.setAvatarUrl(filename);
        saveUser(user);

        return StatusDTO.builder()
                .message(filename)
                .code(CodeTypes.SUCCESS.name())
                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                .build();
    }

    public FileDTO getAvatar(String filename) throws Exception {
        if (filename.contains("..")) {
            throw new ServiceException("Nombre de archivo no válido");
        }

        Path filePath = Paths.get(uploadsPath).resolve(filename).normalize();

        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) {
            throw new ServiceException("Avatar no encontrado");
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return FileDTO.builder()
                .resource(resource)
                .contentType(contentType)
                .build();
    }

    private User checkAuthUser(String username, String password) throws Exception {
        User user = getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (user.getProvider() != null && !user.getProvider().equals(AuthProviderType.LOCAL) && user.getPassword() == null) {
            throw new ServiceException("Este usuario inició sesión con un proveedor externo. No tiene contraseña configurada.");
        }

        if (user.getPassword() != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }

        return user;
    }
}
