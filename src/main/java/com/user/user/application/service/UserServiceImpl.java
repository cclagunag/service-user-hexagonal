package com.user.user.application.service;

import com.user.user.application.DTOS.UserDTO;
import com.user.user.application.command.UserCreateCommand;
import com.user.user.application.command.UserUpdateCommand;
import com.user.user.application.mapper.UserCommandMapper;
import com.user.user.application.mapper.UserDTOMapper;
import com.user.user.application.port.in.IUserService;
import com.user.user.domain.exceptions.ResourceNotFoundException;
import com.user.user.domain.exceptions.UniqueConstraintException;
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
            throw new UniqueConstraintException("Usuario", "username", user.getUsername());
        User userCreate = commandMapper.CreateCommandToModel(user);
        if (userCreate.getPassword() != null && !userCreate.getPassword().isBlank()) {
            userCreate.setPassword(passwordEncoder.encode(userCreate.getPassword()));
        }
        return dtoMapper.ModelToDTO(userRepository.save(userCreate));
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return dtoMapper.ModelToDTOOptional(user);

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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", user.getId()));

        Optional<User> model = userRepository.findByUsername(user.getUsername());
        if(model.isPresent() && !model.get().getId().equals(user.getId()))
            throw new UniqueConstraintException("Usuario", "username", user.getUsername());
        User userUpdate = commandMapper.UpdateCommandToModel(user);
        if (userUpdate.getPassword() != null && !userUpdate.getPassword().isBlank()) {
            userUpdate.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        return dtoMapper.ModelToDTO(userRepository.save(userUpdate));
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
