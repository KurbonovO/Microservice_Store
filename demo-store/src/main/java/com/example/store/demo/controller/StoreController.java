package com.example.store.demo.controller;

import com.example.store.demo.entity.Item;
import com.example.store.demo.entity.Order;
import com.example.store.demo.entity.OrderItem;
import com.example.store.demo.service.InventoryService;
import com.example.store.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StoreController {
    private Logger logger = LoggerFactory.getLogger(StoreController.class);

    Map<String, List<Item>> cartMap = new HashMap<>();

    @Autowired
    private InventoryService service;
    @Autowired
    private OrderService orderService;

    @GetMapping("/inventory/main")
    public String main(Model model) {
        List<Item> items =  service.getItems();
        model.addAttribute("items", items);
        return "inventory";
    }

    @GetMapping("/inventory/create")
    public String create(Item item) {
        return "create-item";
    }

    @PostMapping("/inventory/create")
    public String save(Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-item";
        }
        service.createItem(item);
        List<Item> items =  service.getItems();
        model.addAttribute("items", items);
        return "inventory";
    }

    @GetMapping("/orders/main")
    public String orders(HttpSession httpSession, Model model) {
        List<Order> orders =  orderService.getOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/store/main")
    public String store(HttpSession httpSession, Model model) {
        List<Item> items =  service.getItems();
        model.addAttribute("items", items);
        return "store";
    }

    @GetMapping("/store/cart")
    public String cart(HttpSession httpSession, Model model) {
        List<Item> cart =  cartMap.get(httpSession.getId());
        model.addAttribute("items", cart);
        return "cart";
    }

    @GetMapping("/store/cart/add/{id}")
    public String addToCart(@PathVariable("id") Integer id, HttpSession httpSession) {
        List<Item> cart =  cartMap.get(httpSession.getId());
        if(cart == null){
            cart = new ArrayList<>();
            cartMap.put(httpSession.getId(), cart);
        }
        Item item = service.getItem(id);
        item.setQty(1);//For sake of simplicity
        cart.add(item);
        cartMap.put(httpSession.getId(), cart);
        return "redirect:/store/main";
    }

    @GetMapping("/store/cart/delete/{id}")
    public String deleteFromCart(@PathVariable("id") Integer id, HttpSession httpSession) {
        List<Item> cart =  cartMap.get(httpSession.getId());
        cart.removeIf(c -> c.getId() == id);
        cartMap.put(httpSession.getId(), cart);
        return "redirect:/store/main";
    }

    @GetMapping("/store/cart/checkout")
    public String checkout(HttpSession httpSession, Model model) {
        List<Item> cart =  cartMap.get(httpSession.getId());
        model.addAttribute("items", cart);
        return "cart";
    }

    @GetMapping("/store/cart/order")
    public String placeOrder(HttpSession httpSession) {
        List<Item> cart =  cartMap.get(httpSession.getId());
        Order newOrder = new Order();
        cart.stream().forEach(c -> newOrder.addItem(new OrderItem(c.getId(), c.getQty(), c.getPrice())));
        orderService.createOrder(newOrder);
        return "redirect:/store/main";
    }


}
