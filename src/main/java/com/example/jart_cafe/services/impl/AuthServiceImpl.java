package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.dto.SignupDTO;
import com.example.jart_cafe.dto.UserDTO;
import com.example.jart_cafe.model.User;
import com.example.jart_cafe.repositories.UserRepository;
import com.example.jart_cafe.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getUserId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        return userDTO;
    }
    public UserDTO getUserByEmail(String email ){
        User findUserDb = userRepository.findFirstByEmail(email);
        UserDTO user = new UserDTO();
        user.setEmail(findUserDb.getEmail());
        user.setName(findUserDb.getName());
        return user;
    }
}
