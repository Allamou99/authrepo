package com.example.authservice.Controllers;

import com.example.authservice.Model.UserDetailsModel;
import com.example.authservice.Services.AuthService;
import com.example.authservice.dtos.LoginRequestDTO;
import com.example.authservice.dtos.RefreshTokenRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/login")
    public String getTest(){
        return "helloo";
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return authService.refreshToken(refreshTokenRequestDTO);
    }

    @GetMapping("/userDetails/{username}")
    public UserDetailsModel userDetails(@PathVariable("username") String username){
        UserDetails us =  this.userDetailsService.loadUserByUsername(username);
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setUsername(us.getUsername());
        userDetailsModel.setAuthorities(us.getAuthorities());
        userDetailsModel.setPassword(us.getPassword());
        return userDetailsModel;
    }
    @GetMapping("/checkproducts/{username}")
    public boolean StillAddProductorNot(@PathVariable("username") String username){
        return this.authService.checkingProductsNumbersofUser(username);
    }
}

