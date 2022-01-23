package com.example.authservice.SecurityConfig;

import com.example.authservice.Model.Role;
import com.example.authservice.Model.User;
import com.example.authservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);
        User user = userOp.orElseThrow(()-> new UsernameNotFoundException("The username "+ username+"is invalid"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(this.getAuthorities(user)).build();
        return userDetails;
    }
    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Role> roles = user.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
        }
        return authorities;
    }
}

