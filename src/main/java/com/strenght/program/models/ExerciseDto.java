package com.strenght.program.models;

import com.strenght.program.entities.VideoLink;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExerciseDto {

    private String description;
    private List<String> videoLinks;

}
