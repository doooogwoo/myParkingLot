package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.Model.Vehicle;
import com.MyParkingLot.Damo.Service.parkingService.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VehicleCommandManagerTest {

    private ParkingService parkingService;
    private VehicleCommandManager commandManager;

    //原本 Spring 會幫我們注入（像是 @Autowired），但這裡沒用 Spring，所以要自己 new 跟 mock
    @BeforeEach//測試前準備用的東西
    void setUP(){
        parkingService = mock(ParkingService.class);
        //假裝有這個服務（不啟動整個系統，只測你要測的類別）

        commandManager = new VehicleCommandManager();
    }

    @Test //test RunAll executesCommandsInOrder and RecordsHistory
    void test1(){
        Vehicle v1 = new Vehicle();
        v1.setVehicleId(1L);
        v1.setLicense("ABC-123");
        Vehicle v2 = new Vehicle();
        v2.setVehicleId(2L);
        v2.setLicense("XYZ-456");

        //開始模擬
        VehicleCommand cmd1 = new EnterVehicleCommand(v1,parkingService);
        VehicleCommand cmd2 = new EnterVehicleCommand(v2,parkingService);
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

        // 驗證紀錄
        System.out.println("🚗 History: " + commandManager.getHistory());

        List<String> history = commandManager.getHistory();

        assert history.size() == 3;
        assert history.get(0).contains("ABC-123");
        assert history.get(1).contains("XYZ-456");
        assert history.get(2).contains("ABC-123");

    }
}
