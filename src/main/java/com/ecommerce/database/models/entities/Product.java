package com.ecommerce.database.models.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer categoryId;

    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer price;
    private Integer stock;

    private String desc;
    private String unit;
    private String brand;
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(targetEntity = SaleItem.class, mappedBy = "productId")
    private Set<SaleItem> sales;
}
