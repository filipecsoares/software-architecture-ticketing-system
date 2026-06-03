package com.fcs.customers.usecase.model;

import java.time.LocalDateTime;
import java.util.List;

public record EventCustomerResponseModel (Integer id, String name, LocalDateTime startDate, LocalDateTime endDate, List<SessionResponseModel> sessions){
}
