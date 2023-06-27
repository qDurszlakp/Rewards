FROM amazoncorretto:17-alpine-jdk
MAINTAINER Rewards
COPY target/Rewards-0.0.1-SNAPSHOT.jar Rewards-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Rewards-0.0.1-SNAPSHOT.jar"]