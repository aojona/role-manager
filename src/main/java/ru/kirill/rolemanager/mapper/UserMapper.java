package ru.kirill.rolemanager.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kirill.rolemanager.dto.AllUserResponse;
import ru.kirill.rolemanager.dto.UserRequest;
import ru.kirill.rolemanager.dto.UserResponse;
import ru.kirill.rolemanager.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void setUpMapper() {
        modelMapper
                .createTypeMap(UserRequest.class, User.class)
                .addMappings(mapping -> mapping.skip(User::setRoles));
    }


    public UserResponse toUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public AllUserResponse toAllUserResponse(User user) {
        return modelMapper.map(user, AllUserResponse.class);
    }

    public User toEntity(UserRequest userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public void updateEntity(UserRequest source, User entityToUpdate) {
        modelMapper.map(source, entityToUpdate);
    }
}
