package com.strenght.program.models;

import com.strenght.program.entities.Comment;
import com.strenght.program.entities.Exercise;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class WodDto {

    private String localDate;
    private String hashTag;
    private String myComment;

    private List<ExerciseDto> exercises;
}
