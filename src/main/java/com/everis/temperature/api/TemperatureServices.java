package com.everis.temperature.api;

import com.everis.temperature.entity.TemperatureEntity;
import com.everis.temperature.entity.TemperatureResponse;
import com.everis.temperature.repository.TemperatureRepository;
import com.everis.temperature.service.TemperatureService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;

@RestController
public class TemperatureServices {

    @Resource
    TemperatureService temperatureService;
    @Resource
    TemperatureRepository temperatureRepository;

    @PostMapping(path = "/", produces= MediaType.APPLICATION_JSON_VALUE)
    public TemperatureEntity characters(@RequestBody TemperatureEntity temperatureEntity) {

      return temperatureRepository.save(temperatureEntity);

    }
    @GetMapping("/")
    public TemperatureResponse getTemperature(@RequestParam Date date) {


        return temperatureService.getTempResponseByDate(date);

    }
    @GetMapping("/hour")
    public  TemperatureResponse getTemperature2(@RequestParam Date date){

        return temperatureService.getTempResponse(date);

    }

}
