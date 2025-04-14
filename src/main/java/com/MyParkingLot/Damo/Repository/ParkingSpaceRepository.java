package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace,Long> {
}
