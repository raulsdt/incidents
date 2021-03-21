package com.aircall.test.Incidents.adapter.timer;

import com.aircall.test.Incidents.domain.Alarm;
import com.aircall.test.Incidents.port.TimerService;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class TimerServiceAdapter implements TimerService {

  private final AlarmTimerToAlarmConverter converter;

  public TimerServiceAdapter(AlarmTimerToAlarmConverter converter) {
    this.converter = converter;
  }

  @Override
  public void setAlarm(Integer serviceId, Duration duration) {

  }

  @Override
  public Alarm getAlarm(Integer serviceId) {
    return null;
  }

  @Override
  public void removeAlarm(Integer serviceId) {

  }
}
