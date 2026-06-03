package com.fcs.customers.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String street;
    private Integer num;
    private String zipCode;
    private AdditionalAddressDetail additionalAddressDetail;

    public boolean isValidAddress() {
        return street != null &&
                !street.isEmpty() &&
                num != null &&
                zipCode != null &&
                !zipCode.isEmpty() &&
                additionalAddressDetail != null &&
                additionalAddressDetail.isValidAdditionalAddressDetail();
    }
}
