package com.ecommerce.database.models.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(targetEntity = Customer.class, optional = false, cascade = CascadeType.ALL)
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

    @OneToMany(targetEntity = SaleItem.class,fetch = FetchType.EAGER)
    private Set<SaleItem> saleItems;
}