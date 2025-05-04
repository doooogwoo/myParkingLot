package com.MyParkingLot.Damo.Service.Command;

import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VehicleCommandTest {
    private Vehicle mockVehicle;
    private ParkingLot mockLot;
    private ParkingService parkingService;
    private WebSocketService webSocketService;
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp(){
        mockVehicle = mock(Vehicle.class);
        mockLot = mock(ParkingLot.class);
        parkingService = mock(ParkingService.class);
        webSocketService = mock(WebSocketService.class);

        // 準備模擬資料
        when(mockLot.getParkingLotId()).thenReturn(99L);
        when(mockVehicle.getLicense()).thenReturn("TEST-999");
        when(mockVehicle.getVehicleId()).thenReturn(123L);
        when(mockVehicle.getParkingLot()).thenReturn(mockLot);
    }

    @Test
    void testExecute_shouldCallLeaveAndSendWebSocket(){
        LeaveVehicleCommand command = new LeaveVehicleCommand(mockVehicle,parkingService);

        command.execute();

        //驗證是否離場
        verify(parkingService).vehicleLeaving(123L);

        // 驗證是否推播
        verify(webSocketService).sendParkingLotSpaceUpdate(99L);
    }

    @Test
    void testExecute_shouldCallEnterAndSendWebSocket(){
        EnterVehicleCommand command = new EnterVehicleCommand(mockVehicle,parkingService,vehicleRepository);

        command.execute();

        //驗證是否進場
        verify(parkingService).vehicleEntering(mockVehicle);
        verify(webSocketService).sendParkingLotSpaceUpdate(mockVehicle.getParkingLot().getParkingLotId());
    }
}
