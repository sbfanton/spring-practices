package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.*;
import com.sbfanton.oauth.oauthclient.model.mapper.UserMapper;
import com.sbfanton.oauth.oauthclient.repository.UserRepository;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import com.sbfanton.oauth.oauthclient.utils.constants.CodeTypes;
import com.sbfanton.oauth.oauthclient.utils.constants.DateFormatter;
import com.sbfanton.oauth.oauthclient.utils.constants.TokenConstants;
import net.datafaker.Faker;
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

    @Autowired
    private Faker faker;

    public Optional<User> getById(Long id) { return userRepository.findById(id); }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }

    public boolean existsByUsername(String username) { return userRepository.existsByUsername(username); }

    public boolean existsByEmailAndProvider(String email, AuthProviderType provider) { return userRepository.existsByEmailAndProvider(email, provider); }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public AuthResponseDTO login(LoginDTO loginDTO) throws Exception {
        User user = checkAuthUser(loginDTO.getIdentifier(), loginDTO.getPassword());
        return generateAuthResponse(user);
    }

    public AuthResponseDTO register(RegisterDTO registerDTO) throws ServiceException {
        if(existsByUsername(registerDTO.getUsername()))
            throw new ServiceException("El nombre de usuario ya existe");

        if(existsByEmailAndProvider(registerDTO.getEmail(), AuthProviderType.LOCAL)) {
            throw new ServiceException("El email ya existe");
        }

        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .isEmailVerified(false)
                .avatarUrl(registerDTO.getAvatarUrl())
                .web(registerDTO.getWeb())
                .provider(AuthProviderType.LOCAL)
                .providerId(String.valueOf(faker.number().randomNumber()))
                .build();

        saveUser(user);

        return generateAuthResponse(user);
    }

    public UserDTO getUserDTO(Long id) throws Exception {
        User user = getById(id)
                .orElse(null);

        if(user == null)
            throw new ServiceException("Usuario no encontrado");

        return userMapper.userToUserDTO(user);
    }

    public AuthResponseDTO saveUserFromProvider(User user) throws Exception{

        User usr = userRepository.findByProviderAndProviderId(
                user.getProvider(), user.getProviderId())
                .orElse(null);

        if (usr == null) {
            String username = user.getUsername() + "_" + faker.number().numberBetween(1, 1000);

            while(getUserByUsername(username).orElse(null) != null) {
                username = user.getUsername() + "_" + faker.number().numberBetween(1, 1000);
            }
            user.setUsername(username);
            saveUser(user);
        }

        return generateAuthResponse(usr != null ? usr : user);
    }

    public StatusDTO changeUser(Long id, UserDTO userDTO) throws Exception {
        User user = getById(id)
                .orElse(null);

        if(user == null)
            throw new ServiceException("El usuario al que se le quiere editar atributos no existe.");

        if(!user.getUsername().equals(userDTO.getUsername()) && existsByUsername(userDTO.getUsername()))
            throw new ServiceException("El nombre de usuario ya existe");
        if(!user.getEmail().equals(userDTO.getEmail()) && existsByEmail(userDTO.getEmail()))
            throw new ServiceException("El email ya existe");

        user.setUsername(userDTO.getUsername());
        user.setWeb(userDTO.getWeb());
        user.setEmail(userDTO.getEmail());
        saveUser(user);

        return StatusDTO.builder()
                .message("Usuario editado correctamente")
                .code(CodeTypes.SUCCESS.name())
                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                .build();
    }

    public StatusDTO changePassword(Long id, PasswordEditDTO passwordEditDTO) throws Exception {
        User user = getById(id).orElse(null);
        if(user == null)
            throw new ServiceException("Usuario no encontrado");
        checkAuthUser(user.getUsername(), passwordEditDTO.getCurrentPassword());
        user.setPassword(passwordEditDTO.getNewPassword());
        saveUser(user);
        return StatusDTO.builder()
                .message("Contraseña cambiada correctamente.")
                .code(CodeTypes.SUCCESS.name())
                .timestamp(DateFormatter.basicFormatter(DateFormatter.BASIC_FORMATTER))
                .build();
    }

    public StatusDTO changeAvatar(Long id, MultipartFile file) throws Exception {
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

        User user = getById(id).orElse(null);
        if(user == null)
            throw new ServiceException("El usuario no existe");

        String username = user.getUsername();
        String filename = username + "_avatar" + extension;
        Path filepath = Paths.get(uploadsPath, filename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

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

    private User checkAuthUser(String identifier, String password) throws Exception {
        User user = getUserByUsername(identifier)
                .or(() -> getUserByEmail(identifier))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (user.getProvider() != null && !user.getProvider().equals(AuthProviderType.LOCAL) && user.getPassword() == null) {
            throw new ServiceException("Este usuario inició sesión con un proveedor externo. No tiene contraseña configurada.");
        }

        if (user.getPassword() != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, password)
            );
        }

        return user;
    }

    private AuthResponseDTO generateAuthResponse(User user) {
        String accessToken = jwtService.getToken(
                user,
                TokenConstants.ACCESS_TOKEN_DURATION);
        String refreshToken = jwtService.getToken(
                user,
                TokenConstants.REFRESH_TOKEN_DURATION);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
