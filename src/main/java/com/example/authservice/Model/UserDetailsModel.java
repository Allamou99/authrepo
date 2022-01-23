package com.example.authservice.Model;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
import java.util.HashSet;

public class UserDetailsModel {
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    public UserDetailsModel() {
    }

    public UserDetailsModel(String password, String username, Collection<GrantedAuthority> authorities) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}
