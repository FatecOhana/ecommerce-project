package com.ecommerce.dto.command;

import com.ecommerce.dto.Payload;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpsertItemsCommand<T> {
    private final Payload<T> data;
}
