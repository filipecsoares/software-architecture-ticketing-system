package com.fcs.customers.usecase.model;

public record CustomerResponseModel(Integer id, String email, String document, String name, AddressResponseModel address) {
}
