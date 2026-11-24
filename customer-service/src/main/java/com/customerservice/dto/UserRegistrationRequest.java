package com.customerservice.dto;


public record UserRegistrationRequest(
    String fullName,
    String phone,
    String email,
    String password

) {

}
