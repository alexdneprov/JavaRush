package com.javarush.task.task33.task3305;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,visible = true, property = "className", include = JsonTypeInfo.As.PROPERTY)
public class ParkingLot {
    public String name;
    public String city;
    public List<Vehicle> vehicles;

    public ParkingLot(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}