package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.Exception.APIException;
import com.MyParkingLot.Damo.domain.Model.Vehicle;
import com.MyParkingLot.Damo.Service.orchestrator.parkingService.ParkingService;
import org.junit.jupiter.api.Test;
import java.time.Duration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


//測
//-DefaultFeeStrategy
//-CustomRateFeeStrategy
//-PlayerOverrideFeeStrategy
// 是否會正確根據 vehicle 裡的時間算出收費
public class FeeStrategyTest {
    private ParkingService parkingService;
    private FeeStrategyFactory feeStrategyFactory;

    @Test
    void DefaultFeeStrategy() {
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingDuration(Duration.ofHours(5));
        FeeStrategy feeStrategy = new DefaulFeeStrategy();
        int fee = feeStrategy.calculateFee(vehicle);
        assertEquals(150, fee);
    }

    @Test
    void PlayerFeeStrategy() {
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingDuration(Duration.ofHours(5));
        FeeStrategy feeStrategy = new PlayerFeeStrategy(40);
        int fee = feeStrategy.calculateFee(vehicle);
        assertEquals(5 * 40, fee);
    }

    @Test
    void PlayerOverrideFeeStrategy() {
        Vehicle vehicle = new Vehicle();
        vehicle.setParkingDuration(Duration.ofHours(5));
        FeeStrategy feeStrategy1 = new DefaulFeeStrategy();
        FeeStrategy feeStrategy2 = new PlayerFeeStrategy(20);
        FeeStrategy feeStrategy_p = new PlayerFeeStrategy(-1);

        FeeStrategy feeStrategy3 = new PlayerOverrideFeeStrategy(feeStrategy1,feeStrategy2);
        //FeeStrategy feeStrategy4 = new PlayerOverrideFeeStrategy(feeStrategy1,feeStrategy_p);
        FeeStrategy feeStrategy5 = new PlayerOverrideFeeStrategy(feeStrategy1,null);

        int fee = feeStrategy3.calculateFee(vehicle);
        assertEquals(5 * 20, fee);

        int fee2 = feeStrategy5.calculateFee(vehicle);
        assertEquals(5*30,fee2);

    }


    @Test //踩地雷~~~~~~
    void testNegativeRate_shouldThrowException() {
        APIException exception = assertThrows(APIException.class, () -> {
            new PlayerFeeStrategy(-5);
        });

        System.out.println("錯誤訊息：" + exception.getMessage());
        assertEquals("費率設定為負!!", exception.getMessage());
    }
}
