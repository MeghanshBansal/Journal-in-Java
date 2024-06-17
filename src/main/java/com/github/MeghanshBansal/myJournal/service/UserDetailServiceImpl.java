package com.github.MeghanshBansal.myJournal.service;

import com.github.MeghanshBansal.myJournal.entity.User;
import com.github.MeghanshBansal.myJournal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username).orElseThrow(() ->
                new UsernameNotFoundException("user not present with the username: " + username));


        logger.info("Loading user details for username: {}", username);

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> {
                    String roleName = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());

        logger.info("User roles with ROLE_ prefix: {}", authorities);
        return org.springframework.security.core.userdetails.User.builder().
                username(user.getName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
