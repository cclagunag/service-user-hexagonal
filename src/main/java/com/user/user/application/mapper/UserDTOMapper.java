package com.user.user.application.mapper;

import com.user.user.application.DTOS.UserDTO;
import com.user.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDTOMapper {

    public UserDTO ModelToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    public Optional<UserDTO> ModelToDTOOptional(User user) {
        if (user == null) {
            return Optional.empty();
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return Optional.of(dto);
    }

    public UserDTO ModelOptionalToDTOOptional(Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
