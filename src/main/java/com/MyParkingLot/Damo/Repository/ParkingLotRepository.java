package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.Model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
}
