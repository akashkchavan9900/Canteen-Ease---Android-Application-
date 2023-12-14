package com.example.canteenpr.model;

import com.example.canteenpr.History;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryList {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Money_Transfer_History")
    @Expose
    private List<HistoryModel> histrorydetails = null;

    public HistoryList() {

    }

    public HistoryList(String message, List<HistoryModel> histrorydetails) {
        this.message = message;
        this.histrorydetails = histrorydetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HistoryModel> getHistrorydetails() {
        return histrorydetails;
    }

    public void setHistrorydetails(List<HistoryModel> histrorydetails) {
        this.histrorydetails = histrorydetails;
    }
}