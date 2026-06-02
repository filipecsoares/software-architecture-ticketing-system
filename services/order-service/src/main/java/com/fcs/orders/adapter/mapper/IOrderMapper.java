package com.fcs.orders.adapter.mapper;

import com.fcs.orders.adapter.entrypoint.dto.CreateOrderReservationDto;
import com.fcs.orders.usecase.model.CreateOrderReservationRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IOrderMapper {

    CreateOrderReservationRequestModel createOrderReservationDtoToCreateOrderReservationRequestModel(CreateOrderReservationDto requestDto);
}
