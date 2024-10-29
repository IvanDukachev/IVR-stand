package com.example.ivr_stand.repo;

import com.example.ivr_stand.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Integer> {
}
