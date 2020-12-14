package com.everis.temperature.repository;

import com.everis.temperature.entity.TemperatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<TemperatureEntity, Integer> {

}
