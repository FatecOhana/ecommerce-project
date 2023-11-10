package com.ecommerce.dto;

import com.ecommerce.database.models.entities.SaleItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SaleDTO {
    private Integer id;

    private Integer customerId;
    private Integer status;

    private LocalDateTime paymentDate;
    private LocalDateTime emissionDate;
    private LocalDateTime approvalDate;
    private LocalDateTime transportDate;
    private LocalDateTime deliveryDate;

    private String deliveryType;
    private Float deliveryPrice;

    private String obs;

    private String paymentType;
    private String paymentDiscount;
    private Integer installmentPayment;

    private Float total;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<SaleItem> saleItems;
}