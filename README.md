# Mafia

Technical Test created by Raúl Salazar

Company Aircall

## Description
Aircall uses the tool Opsgenie to manage our incident alerts and on-call shifts.

For the sake of this exercise (and not a manifestation of the Not Invented Here syndrome), let's imagine we want to implement an in-house alert notification system.

Here is the architecture of this system:

![Diagram](https://github.com/aircall/technical-test-pager/blob/master/architecture-diagram.png)

Architecture of the Alert Notification System

Through the web console, the Aircall engineer is able to read and edit the Escalation Policy (1). An Escalation Policy references a Monitored Service by its identifier. It contains an ordered list of levels. Each level contains a set of targets. Each target can be of two types: email or SMS. Email Target contains the email address and SMS Target contains the Phone Number. This console also allows the engineer, when the incident is closed, to inform the Pager Service that the Monitored Service is now Healthy (2).

The Monitored Service in case of dysfunction sends an Alert (composed of the Alert Message and the Identifier of the Service) to the Alerting Service (the alerts central place). Those alerts are forwarded to the Pager Service (3). Then this service based on the Escalation Policy of the Monitored Service (available from the EP Service (4)), sends the notifications to all the Targets of the first Policy Level, by mail (5) or SMS (6). After that the service set an external timer for the Acknowledgement Timeout (7). A target must acknowledge the alert within 15-minutes (8). In case the Pager receives a Service Healthy event (2), the Monitored Service is flagged Healthy and no more notifications will be sent until the next Alert. In case of Acknowledgement Timeout (9) with no prior Alert Acknowledgement (8) or Healthy event (2), the Pager Service escalates to the next Policy Level to notify the targets of the second level and set the timer. And so on...

Use Case Scenarios
Here is the list of Use Case Scenarios that specify what the Pager Service needs to accomplish:

Given a Monitored Service in a Healthy State,
when the Pager receives an Alert related to this Monitored Service,
then the Monitored Service becomes Unhealthy,
the Pager notifies all targets of the first level of the escalation policy,
and sets a 15-minutes acknowledgement delay
Given a Monitored Service in an Unhealthy State,
the corresponding Alert is not Acknowledged
and the last level has not been notified,
when the Pager receives the Acknowledgement Timeout,
then the Pager notifies all targets of the next level of the escalation policy
and sets a 15-minutes acknowledgement delay.
Given a Monitored Service in an Unhealthy State
when the Pager receives the Acknowledgement
and later receives the Acknowledgement Timeout,
then the Pager doesn't notify any Target
and doesn't set an acknowledgement delay.
Given a Monitored Service in an Unhealthy State,
when the Pager receives an Alert related to this Monitored Service,
then the Pager doesn’t notify any Target
and doesn’t set an acknowledgement delay
Given a Monitored Service in an Unhealthy State,
when the Pager receives a Healthy event related to this Monitored Service
and later receives the Acknowledgement Timeout,
then the Monitored Service becomes Healthy,
the Pager doesn’t notify any Target
and doesn’t set an acknowledgement delay
Please note that many concurrency issues can happen in this system. For example, is the Pager Service allowed to send 2 notifications to the same target when 2 alerts (same or different one) are received at the same time ? Theis answer to this question is a product decision. For the sake of this exercise, let’s say the answer is “no” (only 1 notification is sent). So take care of this during your design phase. Even if database persistence is not part of the exercise, tell us in your README file what guarantees you expect from the database.



## Stack
* Java 11
* Spring Boot
* Swagger
* jUnit 5

### Dependencies
* spring-boot-starter-web
* spring-boot-starter-test


## Configuration

The following properties can change the behavior:

| __Property__ | __Description__ | __Default value__ |
|--------------|-----------------|-------------------|
| `incidents.alarmTimer.default.duration` | Default duration of alarm (Milis seconds) | `54000` |

## API

You can find the api associated with this domain documented in the `/spec/openapi.yml`.

## Postman
You can find the collection and environment properties associated with local in the `/postman` folder.


## Usage :rocket:
* To run test:

```bash
mvn test
```

Enjoy!! :fire:



