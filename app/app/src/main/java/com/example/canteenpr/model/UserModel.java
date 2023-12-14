package com.example.canteenpr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("pk_id")
    @Expose
    private String pkid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("dob")
    @Expose
    private String bdate;

    @SerializedName("m_number")
    @Expose
    private String mnumber;

    @SerializedName("department")
    @Expose
    private String department;

    @SerializedName("acedemic_year")
    @Expose
    private String acadamic_year;

    @SerializedName("college_id")
    @Expose
    private String colg_id;

    @SerializedName("address")
    @Expose
    private String address;
   @SerializedName("wallet")
    @Expose
    private String wallet;

    @SerializedName("password")
    @Expose
    private String password;


    public UserModel(){

    }

    public UserModel(String message, String pkid, String name, String gender, String bdate, String mnumber, String department, String acadamic_year, String colg_id, String address, String wallet, String password) {
        this.message = message;
        this.pkid = pkid;
        this.name = name;
        this.gender = gender;
        this.bdate = bdate;
        this.mnumber = mnumber;
        this.department = department;
        this.acadamic_year = acadamic_year;
        this.colg_id = colg_id;
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getMnumber() {
        return mnumber;
    }

    public void setMnumber(String mnumber) {
        this.mnumber = mnumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAcadamic_year() {
        return acadamic_year;
    }

    public void setAcadamic_year(String acadamic_year) {
        this.acadamic_year = acadamic_year;
    }

    public String getColg_id() {
        return colg_id;
    }

    public void setColg_id(String colg_id) {
        this.colg_id = colg_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
