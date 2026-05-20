package com.fcs.tickets.entity.vo;

public record TicketUnitPrice(Double value, Currency currency) {

    public boolean isValid() {
        return value != null && currency != null;
    }
}
