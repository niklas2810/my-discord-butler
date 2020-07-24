FROM maven:3.6.3-jdk-14 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package

FROM openjdk:14-jdk-slim
COPY --from=build /usr/src/app/target/discord-butler-*-shaded.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]