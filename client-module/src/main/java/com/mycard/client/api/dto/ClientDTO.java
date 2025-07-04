package com.mycard.client.api.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientDTO {
    private UUID id;
    private String fullName;
    private String email;
    private UUID trainerId;
    private ClientProfileDTO profile;
}