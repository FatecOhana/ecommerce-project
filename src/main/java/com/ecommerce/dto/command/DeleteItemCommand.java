package com.ecommerce.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class DeleteItemCommand {
    private final UUID id;
}
