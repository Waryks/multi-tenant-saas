package com.mycard.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientProfile {
    private int age;
    private double heightCm;
    private double weightKg;
    private String goalDescription;
}
