package com.example.ivr_stand.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

    @UpdateTimestamp
    private Timestamp created_at;

    @CreationTimestamp
    private Timestamp updated_at;
}
