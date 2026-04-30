package com.fcs.events.adapter.dataprovider.http.openfeign;

import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.AllocateRequestEntity;
import com.fcs.events.adapter.dataprovider.http.openfeign.model.room.RoomResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "room-client", url = "${spring.cloud.openfeign.client.config.room-service.api.baseurl}")
public interface RoomFeignClient {

    @GetMapping("/room/{id}")
    RoomResponseEntity getRoom(@PathVariable("id") Integer roomId);

    @PostMapping("/room/{id}/allocation")
    void allocate(@PathVariable("id") Integer roomId, AllocateRequestEntity requestEntity);
}
