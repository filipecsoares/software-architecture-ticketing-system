package com.fcs.customers.adapter.mapper;

import com.fcs.customers.adapter.entrypoint.dto.CreateOrderReservationDto;
import com.fcs.customers.usecase.model.CreateCustomerOrderReservationRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICustomerMapper {

    CreateCustomerOrderReservationRequestModel createOrderReservationDtoToCreateOrderReservationRequestModel(CreateOrderReservationDto requestDto);
}
