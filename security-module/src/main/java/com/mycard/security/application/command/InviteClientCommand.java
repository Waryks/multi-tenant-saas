package com.mycard.security.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InviteClientCommand {
    private UUID trainerId;
    private String email;
}
