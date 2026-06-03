package com.fcs.customers.usecase.model;

public record AddressResponseModel(String street, Integer num, String zipCode, String adoBlock, Integer adoFloor, Integer adoNumber) {
}
