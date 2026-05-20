package com.fcs.tickets.entity;

import com.fcs.tickets.entity.vo.TicketType;
import com.fcs.tickets.entity.vo.TicketUnitPrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    private Integer id;
    private String name;
    private TicketType type;
    private TicketUnitPrice unitPrice;

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && type != null
                && unitPrice != null && unitPrice.isValid();
    }
}