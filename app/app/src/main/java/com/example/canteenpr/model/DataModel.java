package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataModel implements Serializable {
    @SerializedName("Data")
    @Expose
    private RegistrationResult registrationResult;

    public RegistrationResult getRegistrationResult() {
        return registrationResult;
    }

    public void setRegistrationResult(RegistrationResult registrationResult) {
        this.registrationResult = registrationResult;
    }
}
