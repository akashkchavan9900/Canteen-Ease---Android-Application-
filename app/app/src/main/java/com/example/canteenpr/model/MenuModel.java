package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuModel {

    @SerializedName("pk_id")
    @Expose
    private String pkid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("quantity")
    @Expose
    private String quantity;


    @SerializedName("prize")
    @Expose
    private String prize;


    public MenuModel() {
    }

    public MenuModel(String pkid, String name, String quantity, String prize) {
        this.pkid = pkid;
        this.name = name;
        this.quantity = quantity;
        this.prize = prize;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}

