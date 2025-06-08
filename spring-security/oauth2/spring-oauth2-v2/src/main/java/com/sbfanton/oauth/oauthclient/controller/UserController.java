package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.UserDTO;
import com.sbfanton.oauth.oauthclient.model.mapper.UserMapper;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.utils.GenericMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {

        String token = jwtService.getTokenFromRequest(request);
        User user = jwtService.getUserFromToken(token);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        String newToken = jwtService.getToken(user, false);
        Map<String, Object> response = GenericMapper.convertToMap(userDTO);
        response.put("token", newToken);

        return ResponseEntity.ok().body(response);
    }
}
