FROM maven:3.6.3-jdk-14 AS build
COPY src /build/src
COPY pom.xml /build
WORKDIR /build
RUN mvn clean package

FROM openjdk:14-jdk-slim
COPY --from=build /build/target/*-shaded.jar /app/application.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "application.jar"]