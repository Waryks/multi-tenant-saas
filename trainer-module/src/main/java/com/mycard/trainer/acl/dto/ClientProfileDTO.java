package com.mycard.trainer.acl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientProfileDTO {
    private int age;
    private double heightCm;
    private double weightKg;
    private String goalDescription;
}
