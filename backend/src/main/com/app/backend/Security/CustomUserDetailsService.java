package com.app.backend.security;

import com.app.backend.model.User;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.userDetailsService;
import org.springframework.security.CORE.Userdetails.usernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Collections;

@Service

public class CustomUserDetailsService implements UserDetailsService{
    @Autowire
    private UseRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws usernameNotFoundException{
        User user= userRepository.findByUsername(username);
        .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado" + username));


        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getActive(),
            accountNonExpired: true, credentialsNonExpired: true, acc... true, getAuthorities(user);
        )
    }
    private Collection<? extends GrantedAuthority> getAuthorities(User user){
        return Collections.singletonlist(new SimpleGrantedAuthority("ROLE" + user.getRole().name()))
    }

}