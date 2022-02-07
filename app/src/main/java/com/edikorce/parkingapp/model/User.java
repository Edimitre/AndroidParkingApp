package com.edikorce.parkingapp.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "user_table")
public class User implements Serializable {

    @PrimaryKey
    private Long id;

    private String name;

    private String password;

    private List<Car> carList = new ArrayList<>();

    private Role role;

    private List<ParkingSpot> parkingSpotList = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String password, List<Car> carList, Role role, List<ParkingSpot> parkingSpotList) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.carList = carList;
        this.role = role;
        this.parkingSpotList = parkingSpotList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ParkingSpot> getParkingSpotList() {
        return parkingSpotList;
    }

    public void setParkingSpotList(List<ParkingSpot> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", carList=" + carList +
                ", role=" + role +
                ", parkingSpotList=" + parkingSpotList +
                '}';
    }
}
