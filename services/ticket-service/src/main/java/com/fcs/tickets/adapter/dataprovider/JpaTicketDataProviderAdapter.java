package com.fcs.tickets.adapter.dataprovider;

import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicket;
import com.fcs.tickets.adapter.dataprovider.jpa.entity.JpaTicketType;
import com.fcs.tickets.adapter.dataprovider.jpa.repository.JpaTicketRepository;
import com.fcs.tickets.adapter.dataprovider.jpa.repository.JpaTicketTypeRepository;
import com.fcs.tickets.entity.Ticket;
import com.fcs.tickets.usecase.gateway.TicketGateway;
import com.fcs.tickets.usecase.model.CurrencyResponseModel;
import com.fcs.tickets.usecase.model.TicketResponseModel;
import com.fcs.tickets.usecase.model.TicketTypeResponseModel;
import com.fcs.tickets.usecase.model.TicketUnitPriceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public record JpaTicketDataProviderAdapter(JpaTicketRepository jpaTicketRepository, JpaTicketTypeRepository jpaTicketTypeRepository) implements TicketGateway {

    @Override
    public Integer create(final Ticket toCreate) {

        JpaTicket jpaTicket = new JpaTicket();
        jpaTicket.setName(toCreate.getName());
        jpaTicket.setCurrency(toCreate.getUnitPrice().currency().toString());
        jpaTicket.setUnitPrice(toCreate.getUnitPrice().value().toString());

        List<JpaTicketType> existingJpaTicketType = jpaTicketTypeRepository.findByName(toCreate.getType().name());
        if (existingJpaTicketType != null && !existingJpaTicketType.isEmpty()) {
            jpaTicket.setTicketType(existingJpaTicketType.get(0));
        } else {
            JpaTicketType jpaTicketType = new JpaTicketType();
            jpaTicketType.setName(toCreate.getType().name());
            jpaTicketTypeRepository.save(jpaTicketType);
            jpaTicket.setTicketType(jpaTicketType);
        }

        jpaTicketRepository.save(jpaTicket);

        return jpaTicket.getId();
    }

    @Override
    public boolean exists(final String name) {
        Optional<JpaTicket> jpaTicketOptional = jpaTicketRepository.findByName(name);
        return jpaTicketOptional.isPresent();
    }

    @Override
    public List<TicketResponseModel> getAll() {
        return jpaTicketRepository.findAll().stream()
                .map(jpaTicket -> new TicketResponseModel(
                        jpaTicket.getId(),
                        jpaTicket.getName(),
                        TicketTypeResponseModel.valueOf(jpaTicket.getTicketType().getName()),
                        new TicketUnitPriceResponseModel(Double.valueOf(jpaTicket.getUnitPrice()), CurrencyResponseModel.valueOf(jpaTicket.getCurrency())
                        )
                )).toList();
    }

    @Override
    public TicketResponseModel getById(Integer ticketId) {
        Optional<JpaTicket> jpaTicketOptional = jpaTicketRepository.findById(ticketId);
        if (jpaTicketOptional.isPresent()) {
            JpaTicket jpaTicket = jpaTicketOptional.get();
            return new TicketResponseModel(
                    jpaTicket.getId(),
                    jpaTicket.getName(),
                    TicketTypeResponseModel.valueOf(jpaTicket.getTicketType().getName()),
                    new TicketUnitPriceResponseModel(Double.valueOf(jpaTicket.getUnitPrice()), CurrencyResponseModel.valueOf(jpaTicket.getCurrency())));
        }
        return null;
    }

    @Override
    public void delete(Integer ticketId) {
        Optional<JpaTicket> jpaTicketOptional = jpaTicketRepository.findById(ticketId);
        if (jpaTicketOptional.isPresent()) {
            JpaTicket jpaTicket = jpaTicketOptional.get();
            jpaTicketRepository.delete(jpaTicket);
        }
    }
}
