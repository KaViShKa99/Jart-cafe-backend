package com.example.jart_cafe.api;

import com.example.jart_cafe.Util.JwtUtil;
import com.example.jart_cafe.dto.AuthenticationResponse;
import com.example.jart_cafe.services.impl.UserDetailsServiceImpl;
import com.example.jart_cafe.dto.AuthenticationDTO;
import com.example.jart_cafe.services.AuthService;
import io.jsonwebtoken.io.IOException;
import com.example.jart_cafe.dto.SignupDTO;
import com.example.jart_cafe.dto.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
//    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, java.io.IOException {
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, java.io.IOException {
        System.out.println(authenticationDTO);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException("Incorrect username or password!");
             return new ResponseEntity<>("Incorrect username or password!", HttpStatus.BAD_REQUEST);
        } catch (DisabledException disabledException) {
//            return response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return new ResponseEntity<>("User is not activated", HttpStatus.BAD_REQUEST);

        }

        final UserDetails userService = userDetailsServiceImpl.loadUserByUsername(authenticationDTO.getEmail());
        final String jwt = jwtUtil.generateToken(userService.getUsername());



        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.CREATED);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
        System.out.println(signupDTO);
        UserDTO createdUser = authService.createUser(signupDTO);
        System.out.println(createdUser);
        if (createdUser == null){
            return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

//        return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("profile "+username);

        UserDTO userDTO = authService.getUserByEmail(username);
        if (userDTO == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
