package com.sychev.ticket.repositories;

import com.sychev.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    int countTicketsByUidFlight(UUID uidFlight);

    void deleteTicketsByUidFlight(UUID uidFlight);

    List<Ticket> findAllByUidFlight(UUID uidFlight);

    int countTicketsByUidFlightAndClassType(UUID uidFlight, String classType);

    void deleteTicketsByUid(UUID uid);

    Ticket findByUid(UUID uid);

    List<Ticket> findAllByUidPassenger (UUID uidPassenger);
}
