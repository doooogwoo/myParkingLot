package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class VehicleCommandManagerTest {

    private ParkingService parkingService;
    private VehicleCommandManager commandManager;
    private WebSocketService webSocketService;
    private VehicleRepository vehicleRepository;

    //原本 Spring 會幫我們注入（像是 @Autowired），但這裡沒用 Spring，所以要自己 new 跟 mock
    @BeforeEach//測試前準備用的東西
    void setUP(){
        parkingService = mock(ParkingService.class);
        //假裝有這個服務（不啟動整個系統，只測你要測的類別）

        webSocketService = mock(WebSocketService.class);
        vehicleRepository = mock(VehicleRepository.class);

        commandManager = new VehicleCommandManager();
    }

    @Test //test RunAll executesCommandsInOrder and RecordsHistory
    void test1(){
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(1L);

        Vehicle v1 = new Vehicle();
        v1.setVehicleId(1L);
        v1.setLicense("ABC-123");
        v1.setParkingLot(lot);
        Vehicle v2 = new Vehicle();
        v2.setVehicleId(2L);
        v2.setLicense("XYZ-456");
        v2.setParkingLot(lot);

        //開始模擬
        VehicleCommand cmd1 = new EnterVehicleCommand(v1,parkingService,vehicleRepository);
        VehicleCommand cmd2 = new EnterVehicleCommand(v2,parkingService,vehicleRepository);
        VehicleCommand cmd3 = new LeaveVehicleCommand(v1,parkingService);

        //加入manager管理
        commandManager.addCommand(cmd1);
        commandManager.addCommand(cmd2);
        commandManager.addCommand(cmd3);


        // 執行全部
        commandManager.runAll();

        // 驗證是否有依序呼叫 ParkingService 的方法
        verify(parkingService).vehicleEntering(v1);
        verify(parkingService).vehicleEntering(v2);
        verify(parkingService).vehicleLeaving(1L);
        verify(webSocketService, atLeastOnce()).sendParkingLotSpaceUpdate(1L);


        // 驗證紀錄
        System.out.println("🚗 History: " + commandManager.getHistory());

        List<String> history = commandManager.getHistory();

        assert history.size() == 3;
        assert history.get(0).contains("ABC-123");
        assert history.get(1).contains("XYZ-456");
        assert history.get(2).contains("ABC-123");

    }

    @Test
    void testRunOne_shouldExecuteFirstCommandOnly() {
        Vehicle v1 = new Vehicle();
        v1.setVehicleId(1L);
        v1.setLicense("ZXC-999");

        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(5L);
        v1.setParkingLot(lot);

        VehicleCommand cmd1 = new EnterVehicleCommand(v1, parkingService,vehicleRepository);
        commandManager.addCommand(cmd1);

        commandManager.runOne();

        verify(parkingService).vehicleEntering(v1);
        //驗證 webSocketService 是否被呼叫
        verify(webSocketService).sendParkingLotSpaceUpdate(5L);
        assert commandManager.getHistory().size() == 1;
    }

    //空佇列情境測試（防呆設計）
    @Test
    void testRunOne_whenQueueIsEmpty_shouldDoNothing() {
        commandManager.runOne();
        assert commandManager.getHistory().isEmpty(); // 無異常拋出即可 pass
    }

}
