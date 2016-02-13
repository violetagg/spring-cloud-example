# API Gateway
The Spring Cloud Netflix Sidecar includes a simple http api to get all of the instances (i.e. host and port) for a given service.
You can also proxy service calls through an embedded Zuul proxy which gets its route entries from Eureka.

To enable the Sidecar, create a Spring Boot application with `@EnableSidecar`.

Run `http://localhost:10000/routes` in order to see all available routes.
Run `http://localhost:10000/hosts/{serviceId}` in order to receive information fot the given service.

## Build
```mvn clean package```

## Run
```java -jar target/api-gateway-0.0.1-SNAPSHOT.jar```