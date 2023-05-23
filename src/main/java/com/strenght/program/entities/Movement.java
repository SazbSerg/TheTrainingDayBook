package com.strenght.program.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String movementName;
    private Integer oneRepMaximum;
    private Float incrementCoefficient2;
    private Float incrementCoefficient3;
    private Float incrementCoefficient4;
    private Integer firstWaveRepMaximum = 10;
    private Integer secondWaveRepMaximum = 8;
    private Integer thirdWaveRepMaximum = 5;
    private Integer forthWaveRepMaximum = 3;

}
