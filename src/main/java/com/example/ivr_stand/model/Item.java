package com.example.ivr_stand.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@Entity
@Table(name="items")
public class Item {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String key_words;
}
