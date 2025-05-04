package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Service.FeeStrategy.FeeStrategyFactory;
import com.MyParkingLot.Damo.Service.factory.ParkingLotFactory;
import com.MyParkingLot.Damo.Service.factory.VehicleFactory;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.domain.Model.VehicleType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
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

    @Autowired
    private ParkingLotFactory parkingLotFactory;

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
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private FeeStrategyFactory feeStrategyFactory;

    @Test
    void testParkingLotIncomeAfterVehicleLeave() {
        // 1. 建立停車場
        ParkingLot lot = parkingLotFactory.initParkingLot("TestLot");

        lot.setCreateAt(LocalDateTime.now().minusHours(2));
        lot.setExpenses(0);
        parkingLotRepository.save(lot);

        // 2. 建立停車格
        ParkingSpace space = new ParkingSpace();
        space.setOccupied(true);
        space.setParkingLot(lot);
        parkingSpaceRepository.save(space);

        // 3. 建立車輛並模擬進場
        Vehicle vehicle = new Vehicle();
        vehicle.setLicense("TST-1234");
        vehicle.setVehicleEnterTime(LocalDateTime.now().minusHours(2));
        vehicle.setParkingDuration(Duration.ofHours(2));
        vehicle.setParkingLot(lot);
        vehicle.setParkingSpace(space);
        vehicle.setVehicleType(VehicleType.Motorcycle);
        space.setVehicle(vehicle);
        vehicleRepository.save(vehicle);

        // 4. 模擬離場
        parkingService.vehicleLeaving(vehicle.getVehicleId());

        // 5. 驗證收入與費用
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getVehicleId()).orElseThrow();
        ParkingLot updatedLot = parkingLotRepository.findById(lot.getParkingLotId()).orElseThrow();

        assertNotNull(updatedVehicle.getActualLeaveTime());
        assertTrue(updatedVehicle.getFee() > 0, "Fee 應已計算");
        assertEquals(updatedVehicle.getFee(), updatedLot.getIncome(), "收入應等於車輛費用");
    }
}



