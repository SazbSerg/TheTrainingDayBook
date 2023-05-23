package com.strenght.program.repositories;

import com.strenght.program.entities.WoD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WodRepo  extends JpaRepository<WoD, Long> {
}
