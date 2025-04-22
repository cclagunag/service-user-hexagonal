package com.user.user.application.port.in;

import com.user.user.application.DTOS.UserDTO;
import com.user.user.application.command.UserCreateCommand;
import com.user.user.application.command.UserUpdateCommand;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    UserDTO createUser(UserCreateCommand user);

    Optional<UserDTO> getUserById(UUID id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserUpdateCommand user);

    void deleteUser(UUID id);

}
