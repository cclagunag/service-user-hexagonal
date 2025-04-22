package com.user.user.domain.repository;

import com.user.user.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(UUID id);
}
