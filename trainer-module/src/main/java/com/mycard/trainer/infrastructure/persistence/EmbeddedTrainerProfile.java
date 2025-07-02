package com.mycard.trainer.infrastructure.persistence;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EmbeddedTrainerProfile {
    private String bio;
    private String phoneNumber;
    private String specialization;
    private String instagramHandle;
    private String profilePictureUrl;
}
