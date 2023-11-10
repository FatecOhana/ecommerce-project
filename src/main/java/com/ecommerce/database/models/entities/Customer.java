package com.ecommerce.database.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer identify_document;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String image_path;

    private char gender;
    private char status;

    private Date birthdate;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @OneToMany(targetEntity = Sale.class, mappedBy = "customerId")
    private Set<Sale> sales;

}