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

//@CrossOrigin(origins = "http://localhost:5173/")
//@CrossOrigin(origins = "https://jartcafe.com", allowCredentials = "true")
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
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException, java.io.IOException {
        System.out.println(authenticationDTO);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password!");
            return new ResponseEntity<>("Incorrect username or password!", HttpStatus.BAD_REQUEST);
        } catch (DisabledException disabledException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not activated");

        }

        final UserDetails userService = userDetailsServiceImpl.loadUserByUsername(authenticationDTO.getEmail());
        final String jwt = jwtUtil.generateToken(userService.getUsername());



        return ResponseEntity.status(HttpStatus.CREATED).body(jwt);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
        try {
            UserDTO createdUser = authService.createUser(signupDTO);
            if (createdUser == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created, please try again later!");
            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration is successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again later.");
        }
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
