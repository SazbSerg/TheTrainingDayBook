package com.strenght.program.repositories;

import com.strenght.program.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepo extends JpaRepository<Movement, Long> {
    Movement findMovementByMovementName(String movementName);
}
