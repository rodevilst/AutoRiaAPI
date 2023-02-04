package com.example.autoriaapi.service;

import com.example.autoriaapi.models.ERole;
import com.example.autoriaapi.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
private ERole roleSet;
private String username;


}
