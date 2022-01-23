package com.example.authservice.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    private String id;
    private String Username;
    private String Password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private int productNumbers = 3;
}
