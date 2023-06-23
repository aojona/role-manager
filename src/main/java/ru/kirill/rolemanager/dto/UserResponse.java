package ru.kirill.rolemanager.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {

    private String username;
    private Set<RoleDto> roles;
}
