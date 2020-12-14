package com.everis.temperature.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public  class  TemperatureEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Integer id;
    Timestamp date;
    Integer temperature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    //    time: "13:00 - 14:00",
//
//    min: XX,
//
//    max: XX,
//
//    average: XX
}
