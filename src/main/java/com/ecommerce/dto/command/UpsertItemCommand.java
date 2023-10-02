package com.ecommerce.dto.command;

import com.ecommerce.dto.SingleItemPayload;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpsertItemCommand<T> {
    private final SingleItemPayload<T> data;
}
