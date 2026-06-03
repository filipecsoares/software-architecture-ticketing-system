package com.fcs.customers.entity.factory;

import com.fcs.customers.entity.Customer;
import com.fcs.customers.entity.vo.AdditionalAddressDetail;
import com.fcs.customers.entity.vo.Address;
import com.fcs.customers.entity.vo.Document;

public class CustomerFactory {

    public Customer create(String email, String name, String document, String street, Integer num, String zipCode, String adoBlock, Integer adoFloor, Integer adoNum, String password) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName(name);
        customer.setDocument(new Document(document));
        customer.setAddress(new Address(street, num, zipCode, new AdditionalAddressDetail(adoBlock, adoFloor, adoNum)));
        customer.setPassword(password);
        return customer;
    }
}
