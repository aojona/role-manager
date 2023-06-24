package ru.kirill.rolemanager.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kirill.rolemanager.dto.RoleDto;
import ru.kirill.rolemanager.entity.Role;
import ru.kirill.rolemanager.repository.RoleRepository;
import ru.kirill.rolemanager.exception.ValidationException;
import java.util.Optional;

@Service
@WebService
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @WebMethod
    public RoleDto createRole(@WebParam(name = "role") @Validated RoleDto roleDto) throws ValidationException {
        return Optional
                .of(roleDto)
                .map(role -> modelMapper.map(role, Role.class))
                .map(roleRepository::saveAndFlush)
                .map(role -> modelMapper.map(role, RoleDto.class))
                .orElseThrow();
    }
}
