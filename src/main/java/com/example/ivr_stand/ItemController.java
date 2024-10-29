package com.example.ivr_stand;

import com.example.ivr_stand.model.Item;
import com.example.ivr_stand.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    ItemRepo item;

    @GetMapping("/getItems")
    public List<Item> getItems(){
        return  item.findAll();
    }
}
