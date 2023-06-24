package ru.kirill.rolemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.List;

@Data
public class UserRequest {

    @NotBlank(message = "{error.name}")
    private String username;

    @Pattern(regexp = "^[A-Z]\\w*[0-9]\\w*$", message = "{error.password}")
    private String password;

    private List<RoleDto> roles;
}
