package com.user.user.application.service;

import com.user.user.application.DTOS.UserDTO;
import com.user.user.application.command.UserCreateCommand;
import com.user.user.application.command.UserUpdateCommand;
import com.user.user.application.mapper.UserCommandMapper;
import com.user.user.application.mapper.UserDTOMapper;
import com.user.user.application.port.in.IUserService;
import com.user.user.domain.model.User;
import com.user.user.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCommandMapper commandMapper;
    private final UserDTOMapper dtoMapper;

    @Override
    public UserDTO createUser(UserCreateCommand user) {

        Optional<User> model = userRepository.findByUsername(user.getUsername());
        if(model.isPresent())
            throw new IllegalArgumentException("Usuario con nombre: "+user.getUsername()+ " YA Existe");
        User userCreate = commandMapper.CreateCommandToModel(user);
        if (userCreate.getPassword() != null && !userCreate.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userCreate.getPassword()));
        }
        return dtoMapper.ModelToDTO(userRepository.save(userCreate));
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(dtoMapper::ModelToDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(dtoMapper::ModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserUpdateCommand user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID: " + user.getId() + " no existe"));

        Optional<User> model = userRepository.findByUsername(user.getUsername());
        if(model.isPresent() && !model.get().getId().equals(user.getId()))
            throw new IllegalArgumentException("Usuario con nombre: "+user.getUsername()+ " YA Existe");
        User userUpdate = commandMapper.UpdateCommandToModel(user);
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        return dtoMapper.ModelToDTO(userRepository.save(userUpdate));
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
