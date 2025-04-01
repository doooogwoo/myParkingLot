package com.MyParkingLot.Damo.Exception;


public class ResourceNotFoundException extends RuntimeException{
    String resourceName; // 代表哪種類型的資源（例如 "User", "Product", "Order"）
    Object field;
    String fieldName;    // 代表查詢的實際值（當查詢的是字串時，例如 "user@example.com"）
    int filedTimeId; //for time

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s",resourceName,field,fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, Object fieldId) {
        super(String.format("%s not found with %s: %d",resourceName,field,fieldId));
        this.resourceName = resourceName;
        this.field = field;
    }
}
