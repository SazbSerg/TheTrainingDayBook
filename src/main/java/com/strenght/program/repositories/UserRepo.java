package com.strenght.program.repositories;

import com.strenght.program.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
