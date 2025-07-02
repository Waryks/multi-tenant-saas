package com.mycard.client.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EmbeddedClientProfile {
    private int age;
    private double heightCm;
    private double weightKg;
    private String goalDescription;
}
