package com.strenght.program.repositories;

import com.strenght.program.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepo extends JpaRepository<Exercise, Long> {
}
