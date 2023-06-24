package ru.kirill.rolemanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {

    @NotBlank(message = "{error.name}")
    private String name;
}
