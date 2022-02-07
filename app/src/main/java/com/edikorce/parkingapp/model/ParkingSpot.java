package com.edikorce.parkingapp.model;

import java.io.Serializable;

public class ParkingSpot implements Serializable {


    private Long id;

    private String name;

    private Boolean isTaken;

    private String bookedAt;

    private User user;

    private Car car;

    public ParkingSpot() {
    }

    public ParkingSpot(Long id, String name, Boolean isTaken, String bookedAt, User user, Car car) {
        this.id = id;
        this.name = name;
        this.isTaken = isTaken;
        this.bookedAt = bookedAt;
        this.user = user;
        this.car = car;
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

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }

    public String getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(String bookedAt) {
        this.bookedAt = bookedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isTaken=" + isTaken +
                ", bookedAt='" + bookedAt + '\'' +
                ", user=" + user +
                ", car=" + car +
                '}';
    }
}
