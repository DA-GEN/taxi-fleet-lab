package com.dagen.taxifleet.data;

public class FleetDataException extends RuntimeException {

    public FleetDataException(String message) {
        super(message);
    }

    public FleetDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
