package com.example.sandbox.util.constans;

import lombok.Getter;

@Getter
public enum PetStatus {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String status;

    PetStatus(String status) {
        this.status = status;
    }

    // Method to check if a given string is a valid status
    public static boolean isValidStatus(String value) {
        for (PetStatus status : PetStatus.values()) {
            if (status.getStatus().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
