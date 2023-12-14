package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuList {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Reporting_List")
    @Expose
    private List<MenuModel> totalmenu = null;



    public MenuList() {

    }
    public MenuList(String message, List<MenuModel> totalmenu) {
            this.message = message;
            this.totalmenu = totalmenu;
        }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MenuModel> getTotalmenu() {
        return totalmenu;
    }

    public void setTotalmenu(List<MenuModel> totalmenu) {
        this.totalmenu = totalmenu;
    }
}
