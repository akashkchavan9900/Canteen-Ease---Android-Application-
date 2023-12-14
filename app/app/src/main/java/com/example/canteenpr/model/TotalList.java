package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalList {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Reporting_List")
    @Expose
    private List<UserModel> totaldetails = null;


    public TotalList(){

    }

    public TotalList(String message, List<UserModel> totlaTransaction) {
        this.message = message;
        this.totaldetails = totaldetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserModel> getTotaldetails() {
        return totaldetails;
    }

    public void setTotaldetails(List<UserModel> totaldetails) {
        this.totaldetails = totaldetails;
    }
}
