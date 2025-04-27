package com.MyParkingLot.Damo.Payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private int pakingLotTotal;
    private int balance;
}
