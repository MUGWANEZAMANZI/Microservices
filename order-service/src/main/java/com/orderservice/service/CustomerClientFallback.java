package com.orderservice.service;

import com.orderservice.client.CustomerClient;
import com.orderservice.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientFallback implements CustomerClient {
    @Override
    public CustomerResponse getCustomer(Long id) {
        return null;
    }
}
