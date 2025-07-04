package com.mycard.trainer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    private UUID id;
    private String fullName;
    private String email;

    // TrainerProfile fields
    private String bio;
    private String phoneNumber;
    private String specialization;
    private String instagramHandle;
    private String profilePictureUrl;
}
