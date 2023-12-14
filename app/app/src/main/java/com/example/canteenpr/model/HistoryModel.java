package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryModel {
    @SerializedName("pk_id")
    @Expose
    private String pkid;
    @SerializedName("sender_phone")
    @Expose
    private String sender_phone;
    @SerializedName("receiver_phone")
    @Expose
    private String receiver_phone;
    @SerializedName("receiver_name")
    @Expose
    private String receiver_name;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("created_date")
    @Expose
    private String created_date;


    public HistoryModel(){

    }

    public HistoryModel(String pkid, String receiver_name, String sender_phone, String receiver_phone, String amount, String created_date) {
        this.pkid = pkid;
        this.receiver_name = receiver_name;
        this.sender_phone = sender_phone;
        this.receiver_phone = receiver_phone;
        this.amount = amount;
        this.created_date = created_date;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}