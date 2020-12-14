package com.everis.temperature.service;

import com.everis.temperature.entity.TemperatureEntity;
import com.everis.temperature.entity.TemperatureResponse;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperatureService {

  @PersistenceContext
  EntityManager em;

  public TemperatureResponse getTempResponseByDate(Date date) {

    return getTempResponseByDate(tempList(date),date);

  }

  public TemperatureResponse getTempResponseByDate(List<TemperatureEntity> list, Date date) {

    TemperatureResponse temperatureResponse = new TemperatureResponse();

    IntSummaryStatistics stats = list
        .stream()
        .collect(Collectors.summarizingInt(TemperatureEntity::getTemperature));

    temperatureResponse.setMin(stats.getMin());
    temperatureResponse.setMax(stats.getMax());
    temperatureResponse.setAverage(stats.getAverage());
    temperatureResponse.setDate(date);

    return temperatureResponse;
  }

  public TemperatureResponse getTempResponse(Date date) {
    return getTempResponse(tempList(date), date);
  }

  public TemperatureResponse getTempResponse(List<TemperatureEntity> list, Date date) {

    TemperatureResponse temperatureResponse = new TemperatureResponse();

    IntSummaryStatistics stats = list
        .stream().map(TemperatureEntity::getTemperature)
        .collect(Collectors.summarizingInt(Integer::intValue));

    Timestamp minHour = list
        .stream().map(TemperatureEntity::getDate)
        .min(Timestamp::compareTo)
        .orElse(null);

    Timestamp maxHour = list
        .stream().map(TemperatureEntity::getDate)
        .max(Timestamp::compareTo)
        .orElse(null);

    assert minHour != null;
    date.setTime(minHour.getTime());
    String strMin = new SimpleDateFormat("HH:mm:ss").format(date);

    assert maxHour != null;
    date.setTime(maxHour.getTime());
    String strMax = new SimpleDateFormat("HH:mm:ss").format(date);

    temperatureResponse.setMin(stats.getMin());
    temperatureResponse.setMax(stats.getMax());
    temperatureResponse.setAverage(stats.getAverage());
    temperatureResponse.setTime("" + strMin + " " + strMax);

    return temperatureResponse;
  }

  private List<TemperatureEntity> tempList(Date date) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<TemperatureEntity> query = cb.createQuery(TemperatureEntity.class);
    Root<TemperatureEntity> root = query.from(TemperatureEntity.class);

    List<Predicate> predicates = new ArrayList<>();

    Calendar calMin = Calendar.getInstance();
    calMin.setTime(date);

    calMin.clear(Calendar.HOUR_OF_DAY);
    calMin.clear(Calendar.MINUTE);
    calMin.clear(Calendar.SECOND);
    calMin.clear(Calendar.MILLISECOND);

    Calendar calMax = Calendar.getInstance();
    calMax.setTime(date);

    calMax.set(Calendar.HOUR_OF_DAY, 23);
    calMax.set(Calendar.MINUTE, 59);
    calMax.set(Calendar.SECOND, 59);
    calMax.set(Calendar.MILLISECOND, 99);

    predicates.add(cb.between(root.get("date"), calMin.getTime(), calMax.getTime()));

    query.select(root);
    query.where(predicates.toArray(new Predicate[]{}));

    return em.createQuery(query).getResultList();
  }
}
