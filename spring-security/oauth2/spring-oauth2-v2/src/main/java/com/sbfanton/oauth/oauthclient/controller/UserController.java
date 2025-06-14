package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.facade.CallbackServiceFacade;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.FileDTO;
import com.sbfanton.oauth.oauthclient.model.dto.PasswordEditDTO;
import com.sbfanton.oauth.oauthclient.model.dto.UserDTO;
import com.sbfanton.oauth.oauthclient.model.mapper.UserMapper;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.service.UserService;
import com.sbfanton.oauth.oauthclient.utils.GenericMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CallbackServiceFacade callbackServiceFacade;

    @GetMapping("/me")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user)
        throws Exception {
        return ResponseEntity.ok().body(
                userService.getUserDTO(user.getUsername()));
    }

    @PostMapping("/me")
    public ResponseEntity<?> editUser(
            @AuthenticationPrincipal User user,
            @RequestBody UserDTO userDTO)
            throws Exception {
        return ResponseEntity.ok().body(
                userService.changeUser(user.getUsername(), userDTO));
    }

    @GetMapping("/me/avatar/{filename}")
    public ResponseEntity<?> getAvatar(@PathVariable String filename)
            throws Exception {
        FileDTO fileDTO = userService.getAvatar(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getContentType()))
                .body(fileDTO.getResource());
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<?> editAvatar(
            @AuthenticationPrincipal User user,
            @RequestParam("avatar") MultipartFile file)
            throws Exception {
        return ResponseEntity.ok().body(
                userService.changeAvatar(user.getUsername(), file));
    }

    @PostMapping("/me/password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody PasswordEditDTO passwordEditDTO)
                    throws Exception{
        return ResponseEntity.ok().body(
                userService.changePassword(user.getUsername(), passwordEditDTO));
    }

    @GetMapping("/me/post-callback")
    public ResponseEntity<?> getUserInfoAfterCallback(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(
                callbackServiceFacade.processAfterCallback(user));
    }
}
