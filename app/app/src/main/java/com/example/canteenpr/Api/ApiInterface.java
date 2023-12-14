package com.example.canteenpr.Api;

import com.example.canteenpr.model.AdminList;
import com.example.canteenpr.model.DataModel;
import com.example.canteenpr.model.HistoryList;
import com.example.canteenpr.model.MenuList;
import com.example.canteenpr.model.TotalList;
import com.example.canteenpr.model.UserListModel;
import com.example.canteenpr.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("registration.php")
    Call<DataModel> RegistrationApi
            (       @Field("name") String  name,
                    @Field("dob") String dob,
                    @Field("m_number") String m_number,
                    @Field("college_id") String college_id,
                    @Field("address") String address,
                    @Field("password") String password,
                    @Field("gender") String gender,
                    @Field("department") String department,
                    @Field("acedemic_year") String acedemic_year,
                    @Field("wallet") String wallet
            );

    @FormUrlEncoded
    @POST("login.php")
    Call<UserModel>userLoginApi
            (
                    @Field("m_number") Strin g m_number,
                    @Field("password")String password
            );

    @FormUrlEncoded
    @POST("owner_login.php")
    Call<UserModel>adminLoginApi
            (@Field("m_number") String m_number,
             @Field("password")String password);


    @FormUrlEncoded
    @POST("getdetails_bymobileno.php")
    Call<TotalList>userProfileApi
            (@Field("m_number") String m_number);



    @GET("getdetails.php")
    Call<MenuList>userMenuApi();



    @FormUrlEncoded
    @POST("money_transfer.php")
    Call<DataModel> TransferApi
            (        @Field("sender_phone") String sender_phone,
                     @Field("receiver_phone") String reciver_phone,
                     @Field("amount") String  amount
            );
    @FormUrlEncoded
    @POST("money_transfer_history.php")
    Call<HistoryList>historyApi
            (@Field("sender_phone") String mnumber);

    @GET("getAllStudents.php")
    Call<UserListModel> StudentListApi();

    @FormUrlEncoded
    @POST("add_student_recharge.php")
    Call<DataModel> StudentRechargeApi
            (        @Field("owner_id") String owner_id,
                     @Field("student_id") String student_id,
                     @Field("amount") String amount,
                     @Field("m_number") String  m_number,
                     @Field("password") String  password
            );

    @FormUrlEncoded
    @POST("get_owner.php")
    Call<AdminList>AdminProfileApi
            (@Field("phone") String phone);


    @FormUrlEncoded
    @POST("make_payment.php")
    Call<DataModel> paymentApi
            (        @Field("sender_phone") String sender_phone,
                     @Field("receiver_phone") String receiver_phone,
                     @Field("amount") String  amount,
                     @Field("password") String  password
            );


    @FormUrlEncoded
    @POST("addMenu.php")
    Call<DataModel> addMenu
            (        @Field("name") String name,
                     @Field("quantity") String quantity,
                     @Field("prize") String  price
            );


}


