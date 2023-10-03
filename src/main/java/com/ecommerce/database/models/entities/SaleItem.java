package com.ecommerce.database.models.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "sale_item")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer productId;
    private Integer saleId;
    private Integer amount;
    private Float discount;
    private Float unityPrice;
    private Float total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}