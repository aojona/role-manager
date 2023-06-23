package ru.kirill.rolemanager.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirill.rolemanager.dto.AllUserResponse;
import ru.kirill.rolemanager.dto.RoleDto;
import ru.kirill.rolemanager.dto.UserRequest;
import ru.kirill.rolemanager.dto.UserResponse;
import ru.kirill.rolemanager.entity.Role;
import ru.kirill.rolemanager.exception.UserNotFoundException;
import ru.kirill.rolemanager.mapper.UserMapper;
import ru.kirill.rolemanager.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@WebService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @WebMethod
    public UserResponse get(Long id) {
        return userRepository
                .findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @WebMethod
    public List<AllUserResponse> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toAllUserResponse)
                .toList();
    }

    @WebMethod
    @Transactional
    public UserResponse create(@WebParam(name = "user") UserRequest dto) {
        return Optional
                .of(dto)
                .map(userMapper::toEntity)
                .map(entity -> {
                    List<Role> dtoRoles = entity.getRoles();
                    if (dtoRoles != null) {
                        entity.setRoles(getEntityRoles(dtoRoles));
                    }
                    return entity;
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::toUserResponse)
                .orElseThrow();
    }

    @WebMethod
    @Transactional
    public UserResponse update(@WebParam(name = "id") Long id, @WebParam(name = "user") UserRequest dto) {
        return userRepository
                .findById(id)
                .map(entityToUpdate -> {
                    userMapper.updateEntity(dto, entityToUpdate);
                    entityToUpdate.setRoles(getNewRoles(dto.getRoles()));
                    return entityToUpdate;
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @WebMethod
    @Transactional
    public UserResponse updateWithRoles(@WebParam(name = "id") Long id, @WebParam(name = "role") List<RoleDto> dtoRoles) {
        return userRepository
                .findById(id)
                .map(entity -> {
                    entity.setRoles(getNewRoles(dtoRoles));
                    return entity;
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::toUserResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @WebMethod
    @Transactional
    public boolean delete(@WebParam(name = "id") Long id) {
        return userRepository
                .findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private List<Role> getEntityRoles(List<Role> roles) {
        return roles
                .stream()
                .map(Role::getName)
                .map(roleService::findByName)
                .collect(Collectors.toList());
    }

    private List<Role> getNewRoles(List<RoleDto> newRoles) {
        return newRoles == null
                ? Collections.emptyList()
                : newRoles
                .stream()
                .map(RoleDto::getName)
                .map(roleService::findByName)
                .collect(Collectors.toList());
    }
}
