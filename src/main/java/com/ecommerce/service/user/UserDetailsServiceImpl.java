package com.ecommerce.service.user;


import com.ecommerce.model.user.User;
import com.ecommerce.repository.user.UserRepository;
import com.ecommerce.helper.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new CustomException("Resource not found!"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true,
                grantedAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }
}