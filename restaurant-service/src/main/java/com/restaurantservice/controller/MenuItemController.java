package com.restaurantservice.controller;

import com.restaurantservice.model.MenuItem;
import com.restaurantservice.service.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {
    private final MenuItemService service;

    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem create(@RequestBody MenuItem item) { return service.create(item); }

    @GetMapping
    public List<MenuItem> findAll() { return service.findAll(); }

    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> findByRestaurant(@PathVariable Long restaurantId) {
        return service.findByRestaurant(restaurantId);
    }

    @GetMapping("/{id}")
    public MenuItem findById(@PathVariable Long id) { return service.findById(id); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
