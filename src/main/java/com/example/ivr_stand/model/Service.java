package com.example.ivr_stand.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Data
@Entity
@Table(name="services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String description;
    private String video_url;

    @Column(name = "category_id")
    private Integer categoryId;

    @CreatedDate
    private Timestamp created_at;

    @LastModifiedDate
    private Timestamp updated_at;
}
