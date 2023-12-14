package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminList {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Reporting_List")
    @Expose
    private List<AdminModel> totaldetails = null;

    public AdminList(){

    }

    public AdminList(String message, List<AdminModel> totaldetails) {
        this.message = message;
        this.totaldetails = totaldetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AdminModel> getTotaldetails() {
        return totaldetails;
    }

    public void setTotaldetails(List<AdminModel> totaldetails) {
        this.totaldetails = totaldetails;
    }
}
