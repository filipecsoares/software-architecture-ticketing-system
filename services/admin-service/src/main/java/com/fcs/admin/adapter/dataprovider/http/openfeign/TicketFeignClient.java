package com.fcs.admin.adapter.dataprovider.http.openfeign;

import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.ticket.TicketResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ticket-client", url = "${spring.cloud.openfeign.client.config.ticket-service.api.baseurl}")
public interface TicketFeignClient {

    @PostMapping("/ticket")
    TicketCreatedResponseEntity create(@RequestBody TicketCreateRequestEntity ticketRequestEntity);

    @GetMapping("/tickets")
    List<TicketResponseEntity> getAll();

    @DeleteMapping("/ticket/{ticketId}")
    void delete(@PathVariable("ticketId") Integer ticketId);
}
