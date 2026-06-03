package com.fcs.customers.entity;

import com.fcs.customers.entity.vo.Address;
import com.fcs.customers.entity.vo.Document;
import lombok.Data;

@Data
public class Customer {

    private Integer id;
    private String email;
    private Document document;
    private String name;
    private Address address;
    private String password;

    public boolean isValid() {
        return email != null && !email.isEmpty() &&
                document != null && document.isValidDocument() &&
                name != null && !name.isEmpty() &&
                address != null && address.isValidAddress() &&
                password != null && !password.isEmpty();
    }
}
