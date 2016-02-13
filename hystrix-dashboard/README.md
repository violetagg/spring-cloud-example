# Hystrix Dashboard
The Hystrix Dashboard displays the health of each circuit breaker.
To run the Hystrix Dashboard annotate your Spring Boot main class with `@EnableHystrixDashboard`.
You then visit `/hystrix` and point the dashboard to an individual instances `/hystrix.stream` endpoint in a Hystrix client application.

## Build
```mvn clean package```

## Run
```java -jar target/hystrix-dashboard-0.0.1-SNAPSHOT.jar```