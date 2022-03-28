package com.example.inventory.demo.rest;

import com.example.inventory.demo.entity.Item;
import com.example.inventory.demo.repo.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InventoryController {
    private Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryRepository repo;

    @GetMapping("/inventory/all")
    public List<Item> getAllItems(){

        return repo.getAll();
    }

    @GetMapping("/inventory/{id}")
    public Item getItem(@PathVariable("id") Integer id ){
        return repo.getItem(id);
    }

    @PostMapping("/inventory/create")
    public Item addItem(@RequestBody Item item){
        logger.info(item.toString());
        repo.addItem(item);
        return item;
    }

    @PutMapping("/inventory/update")
    public Item updateItem(@RequestBody Item item){
        logger.info(item.toString());
        return item;
    }

}
