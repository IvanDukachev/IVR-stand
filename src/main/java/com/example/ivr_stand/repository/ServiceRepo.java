package com.example.ivr_stand.repository;

import com.example.ivr_stand.model.Service;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Integer> {
    List<Service> findByCategoryId(Integer category_id);
}
