package com.example.ivr_stand.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
}
