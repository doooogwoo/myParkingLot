package com.MyParkingLot.Damo.Service.FeeStrategy;

import com.MyParkingLot.Damo.domain.Model.ParkingTicket;
import org.springframework.stereotype.Component;

@Component
public class FeeStrategyFactory {
    public FeeStrategy create(ParkingTicket ticket){
        //先創立物件
        FeeStrategy defaulFeeStrategy = new DefaulFeeStrategy();
        FeeStrategy playerFeeStrategy = (ticket !=null && ticket.hasCustomRate())
                ? new PlayerFeeStrategy(ticket.getRate())
                : null;

        return new PlayerOverrideFeeStrategy(defaulFeeStrategy,playerFeeStrategy);
    }
}
