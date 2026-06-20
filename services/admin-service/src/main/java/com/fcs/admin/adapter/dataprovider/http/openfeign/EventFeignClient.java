package com.fcs.admin.adapter.dataprovider.http.openfeign;

import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.event.EventResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "event-client", url = "${spring.cloud.openfeign.client.config.event-service.api.baseurl}")
public interface EventFeignClient {


    @PostMapping("/event")
    EventCreatedResponseEntity create(@RequestBody EventCreateRequestEntity EventCreateRequestEntity);

    @GetMapping("/events")
    List<EventResponseEntity> getAll();

    @DeleteMapping("/event/{eventId}")
    void delete(@PathVariable("eventId") Integer eventId);
}
