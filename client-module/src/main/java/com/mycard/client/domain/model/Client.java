package com.mycard.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Client {
    private UUID id;
    private String fullName;
    private String email;
    private UUID trainerId;
    private ClientProfile profile;
}
