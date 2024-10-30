package com.example.ivr_stand;

import com.example.ivr_stand.model.Item;
import com.example.ivr_stand.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/getItems")
    public List<Item> getItems() {
        return itemRepo.findAll();
    }


    @GetMapping("/getItem/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id) {
        Optional<Item> item = itemRepo.findById(id);

        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
