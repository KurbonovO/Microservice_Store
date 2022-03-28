package com.example.store.demo.service;

import com.example.store.demo.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Item> getItems() {
        ResponseEntity<List<Item>> responseEntity =
                restTemplate.exchange("http://localhost:8686/DEMO-INVENTORY/inventory/all",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Item>>() {
                        });
        List<Item> items = responseEntity.getBody();
        return items;
    }

    public Item getItem(Integer id) {
        String url = "http://localhost:8686/DEMO-INVENTORY/inventory/{id}";
        ResponseEntity<Item> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Item>() {
                        }, id);
        Item item = responseEntity.getBody();
        return item;
    }

    public void createItem(Item item) {
        restTemplate.postForEntity("http://localhost:8686/DEMO-INVENTORY/inventory/create", item, String.class);
    }

    public void updateItem(Item item) {
        restTemplate.postForEntity("http://localhost:8686/DEMO-INVENTORY/inventory/update", item, String.class);
    }
}
