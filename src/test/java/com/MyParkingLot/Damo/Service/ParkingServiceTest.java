package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Service.factory.VehicleFactory;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
@Transactional
public class ParkingServiceTest {
    @Autowired
    private VehicleFactory vehicleFactory;
    @Autowired
    private TimeManager timeManager;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ParkingService parkingService;

    @BeforeEach
    void setupTime() {
        LocalDateTime gameTime = LocalDateTime.of(2030, 4, 5, 0, 0);
        long realTime = System.currentTimeMillis();
        timeManager.initGameTime(gameTime, realTime);
    }

//    @Test
//    void testVehicleLeaving_CalculatesCorrectFee(){
//        Vehicle vehicle = vehicleFactory.generateVehicle();
//
//        vehicle.setParkingDuration(Duration.ofHours(3));
//        vehicle.setExpectedVehicleLeaveTime(vehicle.getVehicleEnterTime().plusHours(3));
//        vehicleRepository.save(vehicle);
//
//        parkingService.vehicleEntering(vehicle);
//        System.out.println("🚗 ParkingSpace: " + vehicle.getParkingSpace());
//
//        ParkingSpace space = vehicle.getParkingSpace();
//
//        int fee = space.getParkingLot().getParkingTicket().getRate();
//
//        parkingService.vehicleLeaving(vehicle.getVehicleId());
//
//
//        int income = space.getSpaceIncome();
//
//        assertEquals(3 * fee, income);
//    }

}
