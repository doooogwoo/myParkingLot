package com.MyParkingLot.Damo.Service.parkingVehicle;

import com.MyParkingLot.Damo.Model.ParkingSpace;
import com.MyParkingLot.Damo.Model.Vehicle;
import com.MyParkingLot.Damo.Model.VehicleType;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.parkingService.ParkingServiceImpl;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.Service.vehicle.VehicleFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class parkingVehicleTest {
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleFactory vehicleFactory;

    @Mock
    private TimeManager timeManager;
    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Test
    void testVehicleEntering_success(){
        //先建立模擬資料
        ParkingSpace testParkingSpace = new ParkingSpace();

        Vehicle testVehicle = new Vehicle();
        testVehicle.setElectricVehicle(false);
        testVehicle.setHandicapped(true);
        testVehicle.setVehicleType(VehicleType.Motorcycle);
        testVehicle.setLicense("ABC-123");
        testVehicle.setParkingSpace(testParkingSpace);

        testParkingSpace.setOccupied(false);

        //當功能被呼叫時，請不要真的去「產生一台車」，而是直接回傳到testVehicle
        Mockito.when(vehicleFactory.generateVehicle()).thenReturn(testVehicle);
        Mockito.when(parkingSpaceRepository.save(Mockito.any(ParkingSpace.class))).thenReturn(testParkingSpace);

        parkingService.vehicleEntering();

        verify(parkingSpaceRepository).save(Mockito.argThat(
                space -> space.isOccupied() && space.getVehicle().getLicense().equals("ABC-123")
        ));

        System.out.println(">>> 被儲存的 ParkingSpace：" + testParkingSpace);
        System.out.println(">>> 被儲存的 Vehicle：" + testVehicle);

    }

    @Test // 1.進場時間 2.關係 3.是否占用
    void enterTest2(){
        // Arrange：建立測試資料
        ParkingSpace testparkingSpace = new ParkingSpace();
        Vehicle testvehicle = new Vehicle();
        testparkingSpace.setOccupied(false);
        testvehicle.setLicense("ABC-124");

        // 模擬 generateVehicle 回傳 testVehicle
        Mockito.when(vehicleFactory.generateVehicle()).thenReturn(testvehicle);

        Mockito.when(parkingSpaceRepository.save(Mockito.any(ParkingSpace.class)))
                .thenAnswer(invocation ->{
                    ParkingSpace saved = invocation.getArgument(0);
                    // 模擬實際儲存行為（更新 testparkingSpace 狀態）
                    testparkingSpace.setVehicle(saved.getVehicle());
                    testparkingSpace.setOccupied(saved.isOccupied());
                    testvehicle.setParkingSpace(testparkingSpace);
                    return testparkingSpace;
                } );
        //time
        Mockito.when(timeManager.getCurrentGameTime()).thenReturn(LocalDateTime.of(2025, 3, 30, 12, 0));

        // Act：呼叫進場邏輯
        parkingService.vehicleEntering();

        // Assert：

        // 驗證 entryTime 有設到
        assertNotNull( testvehicle.getVehicleEnterTime(),"確認車輛進場時間");

        // 驗證雙向關聯（停車格裡有這台車，車也知道自己停在哪）
        assertEquals(testvehicle,testparkingSpace.getVehicle());
        assertEquals(testparkingSpace,testvehicle.getParkingSpace());

        // 驗證停車格為佔用
        assertTrue(testparkingSpace.isOccupied());
    }

    @Test //1.space狀態 2.離場時間
    void leavingTest3(){
        ParkingSpace testSpace = new ParkingSpace();
        Vehicle testVehicle = new Vehicle();
        testVehicle.setLicense("ABC-123");
        LocalDateTime leavingTime = LocalDateTime.now().minusHours(5);
        testVehicle.setVehicleLeaveTime(leavingTime);
        testVehicle.setVehicleId(1L);
        testVehicle.assignParkingSpace(testSpace);





        //要模擬資料庫查找
        Mockito.when(vehicleRepository.findById(testVehicle.getVehicleId())).thenReturn(Optional.of(testVehicle));

        parkingService.vehicleLeaving(testVehicle.getVehicleId());


        //停車場為空
        assertFalse(testSpace.isOccupied());

        //離場時間
        assertEquals(leavingTime,testVehicle.getVehicleLeaveTime());

        verify(vehicleRepository).save(testVehicle);
        verify(parkingSpaceRepository).save(testSpace);


    }

    @Test //「進場失敗情境」測試（找不到空格 → 拋例外
    void test3(){
        //先模擬已經被占用的車格
        ParkingSpace full1 = new ParkingSpace();
        full1.setOccupied(true);
        ParkingSpace full2 = new ParkingSpace();
        full2.setOccupied(true);

        Mockito.when(parkingSpaceRepository.findAll()).thenReturn(List.of(full1,full2));

//        assertThrows()

    }
}
