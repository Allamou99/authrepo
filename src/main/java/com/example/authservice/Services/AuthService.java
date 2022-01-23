package com.example.authservice.Services;

import com.example.authservice.Exceptions.LoginBadCredentials;
import com.example.authservice.Model.User;
import com.example.authservice.Repository.UserRepository;
import com.example.authservice.SecurityConfig.UserDetailsServiceImpl;
import com.example.authservice.dtos.LoginRequestDTO;
import com.example.authservice.dtos.LoginResponse;
import com.example.authservice.dtos.RefreshTokenRequestDTO;
import com.example.authservice.jwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final jwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = null;
        try{
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new LoginBadCredentials("Username or password incorrect"), HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(LoginResponseBuilder(loginRequestDTO.getUsername()));
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO){
        LoginResponse loginResponse = this.LoginResponseBuilder(refreshTokenRequestDTO.getUsername());
        return ResponseEntity.ok(loginResponse);
    }
    private LoginResponse LoginResponseBuilder(String username){
        String token = jwtUtil.generateToken(this.getUserDetails(username));
        LoginResponse loginResponse = LoginResponse.builder()
                .accesToken(token)
                .username(username)
                .accesTokenExpirationDate(this.jwtUtil.extractExpiration(token))
                .refreshTokenExpirationDate(new Date(System.currentTimeMillis() +this.jwtUtil.refreshTokenDuration()))
                .refreshToken(UUID.randomUUID().toString())
                .build();
        return loginResponse;
    }
    private UserDetails getUserDetails(String username){
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return userDetails;
    }
    public boolean checkingProductsNumbersofUser(String username){
        Optional<User> userOp = userRepository.findByUsername(username);
        User user = userOp.orElseThrow(()-> new UsernameNotFoundException("The username "+ username+"is invalid"));
        if(user.getProductNumbers()<1){
            return false;
        }
        else{
            user.setProductNumbers(user.getProductNumbers()-1);
            userRepository.save(user);
            return true;
        }
    }
}
