package com.fcs.customers.adapter.dataprovider.http.openfeign;

import com.fcs.customers.adapter.dataprovider.http.openfeign.model.events.EventResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "events-client", url = "${spring.cloud.openfeign.client.config.event-service.api.baseurl}")
public interface EventsFeignClient {

    @GetMapping("/events")
    List<EventResponseEntity> getEvents();

    @GetMapping("/event/{id}")
    EventResponseEntity getEventById(@PathVariable("id") Integer eventId);
}
