package com.fcs.customers.adapter.entrypoint.dto;

public record CreateCustomerDto(String email, String document, String name, AddressDto address, String password) {
}
