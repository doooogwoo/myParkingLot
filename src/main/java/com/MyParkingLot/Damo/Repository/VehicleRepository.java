package com.MyParkingLot.Damo.Repository;

import com.MyParkingLot.Damo.domain.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    @Query("SELECT v FROM Vehicle v WHERE v.parkingLot.id = :parkingLotId AND v.actualLeaveTime BETWEEN :start AND :end")
    List<Vehicle> findVehiclesByParkingLotAndActualLeaveTimeBetween(
            @Param("parkingLotId") Long parkingLotId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );



}
