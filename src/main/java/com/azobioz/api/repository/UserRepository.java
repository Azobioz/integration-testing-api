package com.azobioz.api.repository;

import com.azobioz.api.dto.UserResponse;
import com.azobioz.api.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);

    User getUserByEmail(String email);
}
