package com.example.ivr_stand.repository;

import com.example.ivr_stand.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
