package com.ecommerce.database.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

}