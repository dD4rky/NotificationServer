FROM gradle:jdk21-alpine AS build
LABEL authors="dd4rky"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/notification-server.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/notification-server.jar"]