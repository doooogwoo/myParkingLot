package com.MyParkingLot.Damo.Service.parkingService;

import com.MyParkingLot.Damo.Exception.BusinessException;
import com.MyParkingLot.Damo.Exception.ErrorCode;
import com.MyParkingLot.Damo.Exception.ResourceNotFoundException;
import com.MyParkingLot.Damo.Model.ParkingSpace;
import com.MyParkingLot.Damo.Model.ParkingSpaceType;
import com.MyParkingLot.Damo.Model.ParkingTicket;
import com.MyParkingLot.Damo.Model.Vehicle;
import com.MyParkingLot.Damo.Repository.ParkingSpaceRepository;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import com.MyParkingLot.Damo.Service.parkingTicket.ParkingTicketServiceImpl;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import com.MyParkingLot.Damo.Service.vehicle.VehicleFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class ParkingServiceImpl implements ParkingService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final VehicleFactory vehicleFactory;
    private final TimeManager timeManager;
    private final VehicleRepository vehicleRepository;
    private final ParkingTicketServiceImpl parkingTicketService;
    @Autowired
    public ParkingServiceImpl(ParkingSpaceRepository parkingSpaceRepository,
                              VehicleFactory vehicleFactory,
                              TimeManager timeManager,
                              VehicleRepository vehicleRepository,
                              ParkingTicketServiceImpl parkingTicketService ){
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.vehicleFactory = vehicleFactory;
        this.timeManager = timeManager;
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketService = parkingTicketService;
    }
    @Override
    public void vehicleEntering() {
            Vehicle vehicle = vehicleFactory.generateVehicle();

            ParkingSpace parkingSpace = new ParkingSpace();

            vehicle.setVehicleEnterTime(timeManager.getCurrentGameTime());
            vehicle.assignParkingSpace(parkingSpace);

            if (!parkingSpace.isOccupied()) parkingSpace.setOccupied(true);

            ParkingSpaceType type = parkingSpace.getParkingSpaceType();
            if (type == ParkingSpaceType.HandicappedParkingSpace && !vehicle.isHandicapped()){
                throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Handicapped);
            }
            if (type == ParkingSpaceType.ElectricParkingSpace && !vehicle.isElectricVehicle()){
                throw new BusinessException(ErrorCode.VEHICLE_ENTER_NOT_Electric);
            }
            parkingSpaceRepository.save(parkingSpace);
    }


    @Override
    public void vehicleLeaving(Long vehicleId){
            //先找車
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "vehicleId", vehicleId));
            //再找對應車輛的停車場
            ParkingSpace parkingSpace = vehicle.getParkingSpace();

            //萬一車位為空(false)
            if (!parkingSpace.isOccupied()){
                throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_IS_EMPTY);
            }

            vehicle.setParkingSpace(null);
            parkingSpace.setVehicle(null);
            parkingSpace.setOccupied(false);
            //得到費用
            parkingSpace.setSpaceIncome(getSpaceIncome(vehicle));

            vehicleRepository.save(vehicle);
            parkingSpaceRepository.save(parkingSpace);
    }

    private int getSpaceIncome(Vehicle vehicle){
        if (vehicle.getParkingDuration() == null){
            throw new BusinessException(ErrorCode.VEHICLE_LEAVE_SPACE_NO_HOURS);
        }
        Duration vehicleDuration = vehicle.getParkingDuration();
        ParkingTicket parkingTicket = vehicle.getParkingSpace().getParkingLot().getParkingTicket();
        return parkingTicketService.calculateFee(vehicleDuration,parkingTicket.getParkingTicketId());
    }

}
