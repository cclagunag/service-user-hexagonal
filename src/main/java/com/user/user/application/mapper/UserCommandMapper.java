package com.user.user.application.mapper;

import com.user.user.application.command.UserCreateCommand;
import com.user.user.application.command.UserUpdateCommand;
import com.user.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserCommandMapper {

    public User UpdateCommandToModel(UserUpdateCommand command) {
        User user = new User();
        user.setId(command.getId());
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        return user;
    }

    public User CreateCommandToModel(UserCreateCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        return user;
    }

}
