package com.fcs.customers.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdditionalAddressDetail {

    private String block;
    private Integer floor;
    private Integer number;

    public boolean isValidAdditionalAddressDetail() {
        if ((block == null || block.isEmpty()) && floor == null && number == null) {
            // ausencia de complemento
            return true;
        }
        // caso algum campo esteja preenchido, obrigatoriamente adoNumber e adoFloor devem estar
        return number != null && floor != null;
    }
}
