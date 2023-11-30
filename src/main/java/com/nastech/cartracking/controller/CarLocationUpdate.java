package com.nastech.cartracking.controller;

import java.io.Serializable;

/**
 * <H3>
 * CarLocationUpdate
 * </H3>
 *
 * @author manhvud
 * @since 2023/11/16
 */
public class CarLocationUpdate implements Serializable {

    private String carId;
    private double latitude;
    private double longitude;

    public CarLocationUpdate(String carId, double latitude, double longitude) {
        this.carId = carId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CarLocationUpdate{" +
                "carId='" + carId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

