package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Repository.ParkingLotRepository;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.FeeStrategy.FeeStrategyFactory;
import com.MyParkingLot.Damo.Service.observer.ParkingLotIncome;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingServiceImpl;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleLeavingIncomeTest {

    private ParkingLotIncome parkingLotIncome;
    private ParkingLot parkingLot;
    private ParkingSpace parkingSpace;
    private Vehicle vehicle;
    private ParkingServiceImpl parkingService;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;
    private ParkingSpaceRepository parkingSpaceRepository;

    @BeforeEach
    void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        parkingLotRepository = mock(ParkingLotRepository.class);
        parkingSpaceRepository = mock(ParkingSpaceRepository.class); // ✨ 這裡要 mock
        FeeStrategyFactory feeStrategyFactory = mock(FeeStrategyFactory.class);

        parkingLotIncome = new ParkingLotIncome();
        TimeManager timeManager = new TimeManager();
        timeManager.initDefaultTimeIfNeeded();

        parkingService = new ParkingServiceImpl(
                parkingSpaceRepository,  // ✅ 這裡傳進去
                timeManager,
                vehicleRepository,
                null,
                parkingLotRepository,
                feeStrategyFactory,
                parkingLotIncome
        );

        when(feeStrategyFactory.create(any())).thenReturn(vehicle -> 60); // 收費策略固定
        when(parkingSpaceRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // ✨ save() 就回傳自己

        // 準備停車場、停車格、車輛
        parkingLot = new ParkingLot();
        parkingLot.setIncome(0);
        parkingLot.setParkingLotName("測試場");

        parkingSpace = new ParkingSpace();
        parkingSpace.setFloor(1);
        parkingSpace.setOccupied(true);
        parkingSpace.setParkingLot(parkingLot);

        vehicle = new Vehicle();
        vehicle.setLicense("ABC-1234");
        vehicle.setParkingSpace(parkingSpace);
        vehicle.setParkingLot(parkingLot);

        parkingSpace.setVehicle(vehicle); // 🔥 記得雙向設定
    }


    @Test
    void testVehicleLeavingAddsIncome() {
        // Arrange
        parkingLotIncome.registerObserver(parkingLot);

        when(vehicleRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(vehicle));

        // 假設 getSpaceIncome() 固定算出來是 100
        vehicle.setParkingDuration(java.time.Duration.ofHours(2)); // 2小時
        parkingSpace.setSpaceIncome(60); // 硬塞進 60元收費

        int initialIncome = parkingLot.getIncome(); // 應該是 0

        // Act
        parkingService.vehicleLeaving(1L); // 離場

        // Assert
        int afterIncome = parkingLot.getIncome();
        assertTrue(afterIncome > initialIncome, "離場後收入應該增加");
        System.out.println("離場後收入：" + afterIncome);
    }
}