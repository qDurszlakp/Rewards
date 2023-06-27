# Rewards service

### Reference Documentation

Project Rewards service is a java service that calculate the reward points earned for each
customer per month and total.

### Pre requirements
> - **Docker version** v20.10.12+
> - **Apache Maven** v3.6.3+

### Setup

``` 
mvn clean & install
docker build -t rewards:1.0.0 .
docker run -p 8080:8080 rewards:1.0.0
```

### Swagger

> http://localhost:8080/swagger-ui/index.html