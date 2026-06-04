package com.fcs.customers.adapter.dataprovider.http.openfeign;

import com.fcs.customers.adapter.dataprovider.http.openfeign.model.tickets.TicketResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tickets-client", url = "${spring.cloud.openfeign.client.config.ticket-service.api.baseurl}")
public interface TicketsFeignClient {

    @GetMapping("/ticket/{id}")
    TicketResponseEntity getTicketById(@PathVariable("id") Integer id);
}
