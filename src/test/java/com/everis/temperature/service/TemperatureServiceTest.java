package com.everis.temperature.service;

import com.everis.temperature.entity.TemperatureEntity;
import com.everis.temperature.entity.TemperatureResponse;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureServiceTest {

  @Test
  void shouldReturnEqualAverageTemperatureRegarlessOfDate() throws ParseException {

    TemperatureService temperatureService = new TemperatureService();

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");

    TemperatureEntity temperatureEntity1 = new TemperatureEntity();
    temperatureEntity1.setDate(new Timestamp( formatter.parse("21/10/2020/15:33:45").getTime()));
    temperatureEntity1.setTemperature(40);
    temperatureEntity1.setId(1);

    TemperatureEntity temperatureEntity2 = new TemperatureEntity();
    temperatureEntity2.setDate(new Timestamp( formatter.parse("21/10/2020/13:23:45").getTime()));
    temperatureEntity2.setTemperature(80);
    temperatureEntity2.setId(2);

    TemperatureEntity temperatureEntity3 = new TemperatureEntity();
    temperatureEntity3.setDate(new Timestamp( formatter.parse("24/10/2020/15:33:45").getTime()));
    temperatureEntity3.setTemperature(90);
    temperatureEntity3.setId( 3);

    List<TemperatureEntity> temperatureEntityList = new ArrayList<>();
    temperatureEntityList.add(temperatureEntity1);
    temperatureEntityList.add(temperatureEntity2);
    temperatureEntityList.add(temperatureEntity3);

    TemperatureResponse temperatureResponse = temperatureService
        .getTempResponseByDate(temperatureEntityList,new Date( formatter.parse("21/10/2020/15:33:45").getTime()));

    assertEquals(70,temperatureResponse.getAverage());

  }
  @Test
  void shouldReturnEqualTimeMinmimumAndMaximun() throws ParseException {

    TemperatureService temperatureService = new TemperatureService();

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/hh:mm:ss");

    TemperatureEntity temperatureEntity1 = new TemperatureEntity();
    temperatureEntity1.setDate(new Timestamp( formatter.parse("21/10/2020/13:06:22").getTime()));
    temperatureEntity1.setTemperature(40);

    TemperatureEntity temperatureEntity2 = new TemperatureEntity();
    temperatureEntity2.setDate(new Timestamp( formatter.parse("21/10/2020/15:33:45").getTime()));
    temperatureEntity2.setTemperature(60);

    List<TemperatureEntity> temperatureEntityList = new ArrayList<>();
    temperatureEntityList.add(temperatureEntity1);
    temperatureEntityList.add(temperatureEntity2);

    TemperatureResponse temperatureResponse = temperatureService
        .getTempResponse(temperatureEntityList,new Date( formatter.parse("21/10/2020/13:06:22").getTime()));

    assertEquals("13:06:22 15:33:45",temperatureResponse.getTime());

  }

}