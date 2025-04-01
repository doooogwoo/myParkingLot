package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.Model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket,Long> {
}
