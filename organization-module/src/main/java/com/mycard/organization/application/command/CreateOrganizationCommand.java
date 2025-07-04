package com.mycard.organization.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrganizationCommand {
    private String name;
    private String contactEmail;
    private String phoneNumber;
    private String address;
    private String logoUrl;
    private String website;
    private String description;
}
