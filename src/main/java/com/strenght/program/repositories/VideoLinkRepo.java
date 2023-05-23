package com.strenght.program.repositories;

import com.strenght.program.entities.VideoLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoLinkRepo  extends JpaRepository<VideoLink, Long> {
}
