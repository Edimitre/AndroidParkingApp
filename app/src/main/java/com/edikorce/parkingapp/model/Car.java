package com.edikorce.parkingapp.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Car implements Serializable {

    private Long id;


    private String carModel;


    private String plateNumber;

    private List<User> userList = new ArrayList<>();

    public Car(Long id, String carModel, String plateNumber, List<User> userList) {
        this.id = id;
        this.carModel = carModel;
        this.plateNumber = plateNumber;
        this.userList = userList;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carModel='" + carModel + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", userList=" + userList +
                '}';
    }
}
