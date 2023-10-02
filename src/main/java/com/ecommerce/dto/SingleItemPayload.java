package com.ecommerce.dto;

import lombok.Data;

@Data
public class SingleItemPayload<T> {
    private T data;
}
