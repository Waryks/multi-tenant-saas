package com.mycard.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Exercise {
    private String name;
    private int sets;
    private int reps;
    private int restSeconds;
    private String notes;
}