package com.edikorce.parkingapp.apiclient;

import com.edikorce.parkingapp.model.ParkingSpot;
import com.edikorce.parkingapp.model.Receipt;
import com.edikorce.parkingapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {



    //PARKING
    @POST("/parking/save")
    Call<ParkingSpot> addParkingSpot(@Body ParkingSpot parkingSpot);

    @GET("/parking/all")
    Call<List<ParkingSpot>> getAllParkingSpot();

    @GET("parking/all/free")
    Call<List<ParkingSpot>> getAllFreeParkingSpots();

    @GET("parking/all/reserved")
    Call<List<ParkingSpot>> getAllReservedParkingSpots();

    @POST("/empty/removeparking/{userId}/{parkingId}")
    Call<ParkingSpot> freeParkingSpot(@Path("userId") Long userId,@Path("parkingId") Long parkingId);



    // USER CALLS
    @POST("user/save")
    Call<User> createUser(@Body User user);

    @GET("/user/get/pass/{password}/{name}")
    Call<User> getUserByPasswordAndName(@Path("password") String password, @Path("name") String name);

    @POST("user/update")
    Call<User> updateUser(@Body User user);

    @POST("user/addparking/{userId}/{parkingId}")
    Call<User> addUserParking(@Path ("userId") Long userId, @Path("parkingId") Long parkingId);

    @POST("user/removeparking/{userId}/{parkingId}")
    Call<User> removeUserParking(@Path ("userId") Long userId, @Path("parkingId") Long parkingId);

    @GET("/user/delete/{id}")
    Call<User> deleteUser(@Path("id") Long id);


    // RECEIPT

    @POST("/receipt/generate/{userId}/{parkingId}")
    Call<Receipt> generateUserReceipt(@Path("userId") Long userId, @Path("parkingId") Long parkingId);
}
