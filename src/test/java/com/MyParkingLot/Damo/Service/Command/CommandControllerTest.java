package com.MyParkingLot.Damo.Service.Command;


import com.MyParkingLot.Damo.Controller.CommandController;
import com.MyParkingLot.Damo.domain.Model.ParkingLot;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.factory.VehicleFactory;
import com.MyParkingLot.Damo.Service.websocket.WebSocketService;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommandController.class)
@AutoConfigureMockMvc
class CommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleCommandManager commandManager;

    @MockBean
    private VehicleFactory vehicleFactory;

    @MockBean
    private ParkingService parkingService;

    @MockBean
    private WebSocketService webSocketService;

    @Test
    void testEnterCommand_shouldAddCommand() throws Exception {
        Vehicle mockVehicle = new Vehicle();
        mockVehicle.setVehicleId(1L);
        mockVehicle.setLicense("TST-123");

        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(1L);
        mockVehicle.setParkingLot(lot);

        when(vehicleFactory.generateVehicle()).thenReturn(mockVehicle);

        mockMvc.perform(post("/commands/enter"))
                .andExpect(status().isOk())
                .andExpect(content().string("已加入進場指令"));

        verify(commandManager).addCommand(any(EnterVehicleCommand.class));
    }

    @Test
    void testRunAll_shouldExecuteAllCommands() throws Exception {
        mockMvc.perform(post("/commands/runAll"))
                .andExpect(status().isOk())
                .andExpect(content().string("已執行所有指令"));

        verify(commandManager).runAll();
    }

    @Test
    void testRunOne_shouldExecuteOneCommand() throws Exception {
        mockMvc.perform(post("/commands/runOne"))
                .andExpect(status().isOk())
                .andExpect(content().string("已執行一筆指令"));

        verify(commandManager).runOne();
    }
}

