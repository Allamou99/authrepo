package com.example.authservice;

import com.example.authservice.Model.Role;
import com.example.authservice.Model.RoleName;
import com.example.authservice.Model.User;
import com.example.authservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
@EnableEurekaClient
public class AuthserviceApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(AuthserviceApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        this.userRepository.deleteAll();
        Set<Role> roles1 = new HashSet<>();
        Set<Role> roles2 = new HashSet<>();

        Role roleTest = Role.builder()
                .role(RoleName.ROLE_ADMIN)
                .build();
        roles1.add(roleTest);
        Role roleTest2 = Role.builder()
                .role(RoleName.ROLE_NORMAL)
                .build();
        roles2.add(roleTest);
        User user1 = User.builder()
                .firstName("Soufiane")
                .lastName("Allamou")
                .Username("Soufiane99")
                .productNumbers(3)
                .Password(passwordEncoder.encode("Dimahmd123"))
                .roles(roles1)
                .build();
        User user2 = User.builder()
                .firstName("Normal")
                .lastName("Normal")
                .Username("Normal")
                .productNumbers(3)
                .Password(passwordEncoder.encode("Normal"))
                .roles(roles2)
                .build();

        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }
}
