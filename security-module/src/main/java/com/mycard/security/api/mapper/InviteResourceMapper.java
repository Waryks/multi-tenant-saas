package com.mycard.security.api.mapper;

import com.mycard.security.api.dto.InviteClientRequest;
import com.mycard.security.application.command.InviteClientCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface InviteResourceMapper {
    InviteClientCommand toCommand(InviteClientRequest request);
}
