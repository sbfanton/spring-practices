package com.course.springsecuritycourse.service;

import com.course.springsecuritycourse.dto.AuthResponseDTO;
import com.course.springsecuritycourse.dto.LoginDTO;
import com.course.springsecuritycourse.dto.RegisterDTO;

public interface AuthService {
    
    public AuthResponseDTO login(LoginDTO loginDTO);
    public AuthResponseDTO register(RegisterDTO registerDTO);
}
