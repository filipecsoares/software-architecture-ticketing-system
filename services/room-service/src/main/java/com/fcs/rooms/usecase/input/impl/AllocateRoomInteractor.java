package com.fcs.rooms.usecase.input.impl;

import com.fcs.rooms.usecase.gateway.RoomGateway;
import com.fcs.rooms.usecase.input.AllocateRoomInputBoundary;
import com.fcs.rooms.usecase.model.AllocationResponseModel;
import com.fcs.rooms.usecase.model.RoomResponseModel;
import com.fcs.rooms.usecase.presenter.RoomAllocatedPresenter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AllocateRoomInteractor implements AllocateRoomInputBoundary {

    private final RoomGateway roomGateway;
    private final RoomAllocatedPresenter roomAllocatedPresenter;

    public AllocateRoomInteractor(final RoomGateway roomGateway, final RoomAllocatedPresenter roomAllocatedPresenter) {
        this.roomGateway = roomGateway;
        this.roomAllocatedPresenter = roomAllocatedPresenter;
    }

    @Override
    public void execute(final Integer roomId, final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        RoomResponseModel roomResponseModel = roomGateway.getById(roomId);
        if (roomResponseModel == null)
            roomAllocatedPresenter.prepareFailView("A Sala com ID '" + roomId + "' não existe.");
        if (!isValidRange(startDateTime, endDateTime)) {
            roomAllocatedPresenter.prepareFailView("O intervalo de datas não é válido");
        }
        if (roomResponseModel != null && roomResponseModel.allocations() != null && !roomResponseModel.allocations().isEmpty()) {
            List<AllocationResponseModel> allocationsResponseModel = roomResponseModel.allocations().stream()
                    .filter(allocationResponseModel -> isNotEqualExistingAllocation(startDateTime, endDateTime, allocationResponseModel.startDateTime(), allocationResponseModel.endDateTime()))
                    .toList();
            if (!allocationsResponseModel.isEmpty())
                roomAllocatedPresenter.prepareFailView("A Sala já está reservada neste range de data e hora.");
        }
        try {
            roomGateway.allocate(roomId, startDateTime, endDateTime);
        } catch (Exception e) {
            roomAllocatedPresenter.prepareFailView("Ocorreu um erro interno ao alocar a sala.");
        }
    }

    private boolean isValidRange(LocalDateTime requestStartDateTime, LocalDateTime requestEndDateTime) {
        if (requestStartDateTime == null || requestEndDateTime == null) {
            return false;
        }
        return requestStartDateTime.isBefore(requestEndDateTime) && requestEndDateTime.isAfter(requestStartDateTime);
    }

    private boolean isNotEqualExistingAllocation(LocalDateTime requestStartDateTime, LocalDateTime requestEndDateTime, LocalDateTime actualStartDateTime, LocalDateTime actualEndDateTime) {
        return !(requestStartDateTime.isBefore(actualStartDateTime) && requestEndDateTime.isBefore(actualStartDateTime) ||
                requestStartDateTime.isAfter(actualEndDateTime) && requestEndDateTime.isAfter(actualEndDateTime));
    }
}