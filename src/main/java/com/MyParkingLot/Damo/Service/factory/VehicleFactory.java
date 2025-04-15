package com.MyParkingLot.Damo.Service.factory;

import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.domain.Model.VehicleType;
import com.MyParkingLot.Damo.Service.time.TimeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class VehicleFactory {

    private final TimeManager timeManager;
    @Autowired
    public VehicleFactory(TimeManager timeManager){
        this.timeManager = timeManager;
    }
    public Vehicle generateVehicle(){
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(generateVehicleType());
        vehicle.setHandicapped(generateIsHandicapped());
        vehicle.setElectricVehicle(generateIsElectricVehicle());
        vehicle.setLicense(generateLicense());


        //先記錄進場時間
        LocalDateTime enter = timeManager.getCurrentGameTime();
        vehicle.setVehicleEnterTime(enter);
        //再記錄離場時間
        vehicle.setParkingDuration(leavingTime(enter));
        //vehicle.setExpectedVehicleLeaveTime(enter .plus( vehicle.getParkingDuration()));

        log.debug("新車輛 {} 預計停車 {} 分鐘", //，預估離場時間為 {}
                vehicle.getLicense(),
                vehicle.getParkingDuration().toMinutes());
                //vehicle.getExpectedVehicleLeaveTime());

        return vehicle;
    }


    public Duration leavingTime(LocalDateTime enterTime){
        return parkingTotalHour(enterTime.getHour());
    }

    public Duration parkingTotalHour(int hour){
        double probability = Math.random();
        //早上7-10點
        if (hour >= 7 && hour < 10) {
            if (probability < 0.6) return Duration.ofHours(8);
            else if (probability < 0.9) return Duration.ofHours(9);
            else return Duration.ofHours(10);
        }

        else if ( hour > 10 && hour <= 14 ){
            if (probability < 0.8) return Duration.ofHours(1);
            else return Duration.ofHours(2);
        }

        else if (hour > 14 &&  hour <= 17){
            if (probability < 0.5) return  Duration.ofHours(5);
            else return Duration.ofHours(2);
        }

        else if( hour > 17 && hour <= 20) {
            if (probability < 0.4) return Duration.ofHours(3);
            else return Duration.ofHours(2);
        }

        else {
            if (probability < 0.3) return Duration.ofHours(9);
            else if (probability < 0.4) return Duration.ofHours(12);
            else if (probability < 0.7) return Duration.ofHours(7);
            else return Duration.ofHours(15);
        }
    }

    //Overload --> for test


    public VehicleType generateVehicleType(){
        List<VehicleType> vehicleTypes = new ArrayList<>();
        vehicleTypes.add(VehicleType.Car);
        vehicleTypes.add(VehicleType.Motorcycle);
        return vehicleTypes.get((int)  (Math.random() * vehicleTypes.size()));
    }

    public boolean generateIsElectricVehicle(){
        double probability = Math.random();
        return probability < 0.6;
    }
    public boolean generateIsHandicapped(){
        double probability = Math.random();
//        double randomValue = 0.2;
//        if (probability < randomValue) return true;
//        else return false;
        return probability < 0.2;
    }

    public String generateLicense(){
      Random random = new Random();
        char[] alphabet = new char[3];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char)(random.nextInt(26)+'A');
        }
        String str = new String(alphabet);
        String num = String.valueOf(random.nextInt(9999)+1000);
        return str + "-" + num;
    }
}
