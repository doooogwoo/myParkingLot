package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
}
