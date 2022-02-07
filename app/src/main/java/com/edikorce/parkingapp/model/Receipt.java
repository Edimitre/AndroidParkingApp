package com.edikorce.parkingapp.model;

public class Receipt {


    private Long id;

    private User user;

    private Long bookedTimeInMillis;

    private Long releasedTimeInMillis;

    private Long minutesStayed;

    private Double cost;


    public Receipt(Long id, User user, Long bookedTimeInMillis, Long releasedTimeInMillis, Long minutesStayed, Double cost) {
        this.id = id;
        this.user = user;
        this.bookedTimeInMillis = bookedTimeInMillis;
        this.releasedTimeInMillis = releasedTimeInMillis;
        this.minutesStayed = minutesStayed;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBookedTimeInMillis() {
        return bookedTimeInMillis;
    }

    public void setBookedTimeInMillis(Long bookedTimeInMillis) {
        this.bookedTimeInMillis = bookedTimeInMillis;
    }

    public Long getReleasedTimeInMillis() {
        return releasedTimeInMillis;
    }

    public void setReleasedTimeInMillis(Long releasedTimeInMillis) {
        this.releasedTimeInMillis = releasedTimeInMillis;
    }

    public Long getMinutesStayed() {
        return minutesStayed;
    }

    public void setMinutesStayed(Long minutesStayed) {
        this.minutesStayed = minutesStayed;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", user=" + user +
                ", bookedTimeInMillis=" + bookedTimeInMillis +
                ", releasedTimeInMillis=" + releasedTimeInMillis +
                ", minutesStayed=" + minutesStayed +
                ", cost=" + cost +
                '}';
    }
}
