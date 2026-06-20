package com.fcs.admin.adapter.dataprovider.http.openfeign;

import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomCreateRequestEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomCreatedResponseEntity;
import com.fcs.admin.adapter.dataprovider.http.openfeign.model.room.RoomResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "room-client", url = "${spring.cloud.openfeign.client.config.room-service.api.baseurl}")
public interface RoomFeignClient {


    @PostMapping("/room")
    RoomCreatedResponseEntity create(@RequestBody RoomCreateRequestEntity roomCreateRequestEntity);

    @GetMapping("/rooms")
    List<RoomResponseEntity> getAll();

    @DeleteMapping("/room/{roomId}")
    void delete(@PathVariable("roomId") Integer roomId);
}
