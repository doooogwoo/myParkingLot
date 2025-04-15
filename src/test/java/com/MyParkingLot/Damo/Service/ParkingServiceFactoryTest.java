package com.MyParkingLot.Damo.Service;

import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.Command.LeaveVehicleCommand;
import com.MyParkingLot.Damo.Service.Command.VehicleCommandManager;
import com.MyParkingLot.Damo.Service.factory.ParkingServiceFactory;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.ParkingSpace;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingServiceFactoryTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleCommandManager vehicleCommandManager;

    @Mock
    private TimeManager timeManager;

    @Mock
    private ParkingService parkingService;

    @Mock
    private WebSocketService webSocketService;

    @InjectMocks
    private ParkingServiceFactory parkingServiceFactory;

    @Test
    void autoLeaveVehicles_shouldAddLeaveCommand_whenTimeIsUp() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setLicense("TEST-999");
        vehicle.setVehicleEnterTime(LocalDateTime.of(2030, 4, 5, 0, 0));
        vehicle.setParkingDuration(Duration.ofMinutes(30));
        vehicle.setActualLeaveTime(null);

        // 模擬已進場（避免 early return）
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotName("MyLot");
        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceId(101L);
        vehicle.setParkingLot(lot);
        vehicle.setParkingSpace(space);

        // 模擬現在時間比預估離場晚
        when(timeManager.getCurrentGameTime())
                .thenReturn(LocalDateTime.of(2030, 4, 5, 1, 0));

        when(vehicleRepository.findAll())
                .thenReturn(List.of(vehicle));

        // Act
        parkingServiceFactory.autoLeaveVehicles();

        // Assert
        verify(vehicleCommandManager).addCommand(
                argThat(cmd ->
                        cmd instanceof LeaveVehicleCommand &&
                                ((LeaveVehicleCommand) cmd).getVehicle().equals(vehicle))
        );

        verify(webSocketService).sendParkingLotSpaceUpdate(1L);

//        ArgumentCaptor<Command> captor = ArgumentCaptor.forClass(Command.class);
//        verify(vehicleCommandManager).addCommand(captor.capture());
//        LeaveVehicleCommand cmd = (LeaveVehicleCommand) captor.getValue();
//        assertEquals(vehicle, cmd.getVehicle());


    }
}
