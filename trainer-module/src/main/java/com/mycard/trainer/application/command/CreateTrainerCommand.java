package com.mycard.trainer.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateTrainerCommand {
    private String fullName;
    private String email;
    private UUID organizationId;
    private String bio;
    private String phoneNumber;
    private String specialization;
    private String instagramHandle;
    private String profilePictureUrl;
}
