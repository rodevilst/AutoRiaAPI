package com.example.autoriaapi.controllers;

import com.example.autoriaapi.configs.jwt.JwtUtils;
import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.Role;
import com.example.autoriaapi.models.User;
import com.example.autoriaapi.pojo.JwtResponse;
import com.example.autoriaapi.pojo.LoginRequest;
import com.example.autoriaapi.pojo.MessageResponse;
import com.example.autoriaapi.pojo.SignUpRequest;
import com.example.autoriaapi.repository.RoleRepository;
import com.example.autoriaapi.repository.UserRepository;
import com.example.autoriaapi.service.UserDTO;
import com.example.autoriaapi.service.UserDetailsImpl;
import com.example.autoriaapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    UserDetailsImpl userDetails;
    UserDetailsServiceImpl service;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    UserDTO userDTO;
    Role role;
    User user;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : Email is exist"));
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> reqRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository
                                .findByName(ERole.ROLE_MODER)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository
                                .findByName(ERole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(sellerRole);
                        break;
                    case "user":
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PatchMapping("/modreg/{id}")
//    public void setUserToModer(@PathVariable long id, @RequestBody UserDTO userDTO,@RequestParam Map<String,String> form ) {
//        Role user = roleRepository.findById(id).get();
//        user.setName(userDTO.getRoleSet());
//        roleRepository.save(user);
//    }


//    @PostMapping("/modreg/{id}")
//        public String usersave(@RequestParam Map<String,String> form,
//                               @RequestParam("id")User user,
//                               @PathVariable long id){
//
//        Set<String> roles = Arrays.stream(ERole.values()).map(ERole::name).collect(Collectors.toSet());
//        user.getRoles().clear();
//        for (String key : form.keySet()) {
//            if(roles.contains(key)) {
//                user.getRoles().add(Role.valueOf(key));
//            }
//        }
//        userRepository.save(user);
//        return null;
//
//
//
//    }


//    @GetMapping("/modreg/{id}")
//    public String editUser(@PathVariable("id") Long id, Model model) {
//        User user = service.get(id);
//        List<Role> roles = service.getRoles();
//        model.addAllAttributes("user", user);
//        return null;
//    }
}

