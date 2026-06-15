package com.fcs.customers.adapter.mapper;

import com.fcs.customers.adapter.entrypoint.dto.CreateCustomerDto;
import com.fcs.customers.adapter.entrypoint.dto.CreateOrderReservationDto;
import com.fcs.customers.entity.Customer;
import com.fcs.customers.entity.factory.CustomerFactory;
import com.fcs.customers.usecase.model.CreateCustomerOrderReservationRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICustomerMapper {

    default Customer createCustomerDtoToCustomer(CreateCustomerDto requestDto) {
        return new CustomerFactory().create(
                requestDto.email(),
                requestDto.name(),
                requestDto.document(),
                requestDto.address().street(),
                requestDto.address().num(),
                requestDto.address().zipCode(),
                requestDto.address().adoBlock(),
                requestDto.address().adoFloor(),
                requestDto.address().adoNumber(),
                requestDto.password()
        );
    }

    CreateCustomerOrderReservationRequestModel createOrderReservationDtoToCreateOrderReservationRequestModel(CreateOrderReservationDto requestDto);
}
