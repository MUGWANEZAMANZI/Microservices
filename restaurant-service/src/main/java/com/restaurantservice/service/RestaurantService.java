package com.restaurantservice.service;

import com.restaurantservice.model.Restaurant;
import com.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) { return repository.save(restaurant); }
    public List<Restaurant> findAll() { return repository.findAll(); }
    public Restaurant findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
