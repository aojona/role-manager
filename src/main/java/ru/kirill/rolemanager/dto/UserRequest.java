package ru.kirill.rolemanager.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserRequest {

    private String username;
    private String password;
    private List<RoleDto> roles;
}
