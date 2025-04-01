package com.MyParkingLot.Damo.Exception;

import lombok.Getter;

public enum ErrorCode {
    TIME_ERROR("","提供的時間格式不正確，請確認格式為 ISO-8601（yyyy-MM-dd'T'HH:mm:ss）"),
    VEHICLE_ENTER_NOT_Handicapped("P001","無身障證明，勿停入身障停車場"),
    VEHICLE_ENTER_NOT_Electric("P002","非電動車，勿停入電動車位"),
    VEHICLE_LEAVE_SPACE_IS_EMPTY("P003","該車位沒有停車"),
    VEHICLE_LEAVE_SPACE_NO_HOURS("P004","車輛沒有設定停車時數，無法計算");


    @Getter
    private final String code;
    @Getter
    private final String message;



    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
