# Eureka Server
Setup a Netflix Eureka service registry.
A service registry enables client-side load-balancing and decouples service providers from consumers without the need for DNS.

Run `http://localhost:8761/` in order to Eureka dashboard

## Tutorial
http://spring.io/guides/gs/service-registration-and-discovery/

## Build
```mvn clean package```

## Run
```java -jar target/eureka-server-0.0.1-SNAPSHOT.jar```