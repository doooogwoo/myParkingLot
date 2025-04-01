package com.MyParkingLot.Damo.Service.vehicle;

import com.MyParkingLot.Damo.Model.Vehicle;
import com.MyParkingLot.Damo.Payload.VehicleDto;
import com.MyParkingLot.Damo.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService{
    private final VehicleFactory vehicleFactory;
    private final ModelMapper mapper;
    private final VehicleRepository vehicleRepository;
    @Autowired
    public VehicleServiceImpl(VehicleFactory vehicleFactory, ModelMapper mapper, VehicleRepository vehicleRepository) {
        this.vehicleFactory = vehicleFactory;
        this.mapper = mapper;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public VehicleDto addVehicle() {
        Vehicle vehicle = vehicleFactory.generateVehicle();
        vehicleRepository.save(vehicle);
        return mapper.map(vehicle,VehicleDto.class);
    }
}
