package com.mycard.trainer.domain.model;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class TrainerProfile {
    private String bio;
    private String phoneNumber;
    private String specialization;
    private String instagramHandle;
    private String profilePictureUrl;
}
