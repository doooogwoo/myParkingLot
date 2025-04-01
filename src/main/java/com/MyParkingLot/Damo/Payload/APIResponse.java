package com.MyParkingLot.Damo.Payload;

import com.MyParkingLot.Damo.Exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIResponse {
    private String code;
    private boolean status;
    private String message;

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public APIResponse(String code, boolean status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
