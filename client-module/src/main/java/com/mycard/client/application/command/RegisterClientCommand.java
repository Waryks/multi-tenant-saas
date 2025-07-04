package com.mycard.client.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class RegisterClientCommand {
    private UUID trainerId;
    private String fullName;
    private String email;
    private int age;
    private double heightCm;
    private double weightKg;
    private String goalDescription;
}
