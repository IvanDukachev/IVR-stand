package com.example.ivr_stand.repo;

import com.example.ivr_stand.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Integer> {
}
