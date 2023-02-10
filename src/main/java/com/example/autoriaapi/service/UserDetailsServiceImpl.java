package com.example.autoriaapi.service;

import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.Role;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.repository.RoleRepository;
import com.example.autoriaapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username:" + username));
        return UserDetailsImpl.build(user);
    }
}
