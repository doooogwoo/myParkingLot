package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
    Optional<ParkingLot> findByLocationId (String locationId);
}
