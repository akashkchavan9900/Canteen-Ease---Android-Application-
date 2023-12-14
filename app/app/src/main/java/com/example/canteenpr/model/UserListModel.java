package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Registered_Student_List")
    @Expose
    private List<UserModel> userList = null;


    public UserListModel(){

    }

    public UserListModel(String message, List<UserModel> userList) {
        this.message = message;
        this.userList = userList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
