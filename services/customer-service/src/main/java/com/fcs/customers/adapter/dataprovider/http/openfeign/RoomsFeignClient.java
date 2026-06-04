package com.fcs.customers.adapter.dataprovider.http.openfeign;

import com.fcs.customers.adapter.dataprovider.http.openfeign.model.rooms.RoomResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rooms-client", url = "${spring.cloud.openfeign.client.config.room-service.api.baseurl}")
public interface RoomsFeignClient {

    @GetMapping("/room/{id}")
    RoomResponseEntity getRoomById(@PathVariable("id") Integer id);
}
