package com.restaurantservice.service;

import com.restaurantservice.model.MenuItem;
import com.restaurantservice.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository repository;

    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public MenuItem create(MenuItem item) {
        item.setAvailable(true);
        return repository.save(item);
    }

    public List<MenuItem> findAll() { return repository.findAll(); }

    public List<MenuItem> findByRestaurant(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    public MenuItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found: " + id));
    }

    public void delete(Long id) { repository.deleteById(id); }
}
