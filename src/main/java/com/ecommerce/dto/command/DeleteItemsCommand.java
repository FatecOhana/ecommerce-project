package com.ecommerce.dto.command;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
public class DeleteItemsCommand {
    private final Set<UUID> ids;
}
