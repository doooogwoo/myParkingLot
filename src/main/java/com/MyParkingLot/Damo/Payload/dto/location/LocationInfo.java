package com.MyParkingLot.Damo.Payload.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationInfo {
    private int x;
    private int y;
    private boolean used;
}
