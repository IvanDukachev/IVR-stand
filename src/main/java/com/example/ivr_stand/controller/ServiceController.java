package com.example.ivr_stand.controller;

import com.example.ivr_stand.model.Service;
import com.example.ivr_stand.repository.ServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ServiceController {
    @Autowired
    private ServiceRepo serviceRepo;

    @GetMapping("/getItems")
    public List<Service> getItems() {
        return serviceRepo.findAll();
    }


    @GetMapping("/getItem/{id}")
    public ResponseEntity<Service> getItemById(@PathVariable Integer id) {
        Optional<Service> item = serviceRepo.findById(id);

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
