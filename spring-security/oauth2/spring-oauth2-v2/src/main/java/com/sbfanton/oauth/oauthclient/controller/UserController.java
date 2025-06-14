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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CallbackServiceFacade callbackServiceFacade;

    @Autowired
    private UserService userService;

    /*
    @GetMapping("/me/post-callback-info")
    public ResponseEntity<?> getUserInfoAfterCallback(HttpServletRequest request) {
        return ResponseEntity.ok().body(
                callbackServiceFacade.processUserAfterCallback(request));
    }
     */

    @GetMapping("/me")
    public ResponseEntity<?> getUser()
        throws Exception {
        return ResponseEntity.ok().body(userService.getUserDTO());
    }

    @PostMapping("/me")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO)
            throws Exception {
        return ResponseEntity.ok().body(userService.changeUser(userDTO));
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
    public ResponseEntity<?> editAvatar(@RequestParam("avatar") MultipartFile file)
            throws Exception {
        return ResponseEntity.ok().body(userService.changeAvatar(file));
    }

    @PostMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordEditDTO passwordEditDTO)
                    throws Exception{
        return ResponseEntity.ok().body(userService.changePassword(passwordEditDTO));
    }


}
