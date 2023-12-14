package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminModel {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("pk_id")
    @Expose
    private String pkid;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("wallet")
    @Expose
    private String wallet;

    @SerializedName("password")
    @Expose
    private String password;


    public AdminModel(){


    }
    public AdminModel(String message, String pkid, String name, String phone, String wallet, String password) {
        this.message = message;
        this.pkid = pkid;
        this.name = name;
        this.phone = phone;
        this.wallet = wallet;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
