package com.example.jart_cafe.services;

import com.example.jart_cafe.dto.SignupDTO;
import com.example.jart_cafe.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupDTO signupDTO);
    UserDTO getUserByEmail(String email );
}
