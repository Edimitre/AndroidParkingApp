package com.edikorce.parkingapp.localdb;


import androidx.room.TypeConverter;

import com.edikorce.parkingapp.model.Car;
import com.edikorce.parkingapp.model.ParkingSpot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class Converters {

    @TypeConverter
    public String convertCarListToString(List<Car> carList) {
        if (carList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Car>>() {
        }.getType();
        String CarListString = gson.toJson(carList, type);
        return CarListString;
    }

    @TypeConverter
    public List<Car> convertStringToCarList(String carListString) {
        if (carListString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Car>>() {
        }.getType();
        List<Car> carList = gson.fromJson(carListString, type);
        return carList;
    }




    @TypeConverter
    public String convertParkingListToString(List<ParkingSpot> parkingSpotList) {
        if (parkingSpotList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ParkingSpot>>() {
        }.getType();
        String parkingSpotListString = gson.toJson(parkingSpotList, type);
        return parkingSpotListString;
    }

    @TypeConverter
    public List<ParkingSpot> convertStringToParkingList(String parkingSpotList) {
        if (parkingSpotList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ParkingSpot>>() {
        }.getType();
        List<ParkingSpot> parkingList = gson.fromJson(parkingSpotList, type);
        return parkingList;
    }

}
