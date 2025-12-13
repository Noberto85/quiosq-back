FROM maven:3.8.4-openjdk-17-slim AS build

COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:17

ARG PROFILE
ARG PROFILE_ARG
ENV PROFILE = ${PROFILE}
ENV PROFILE_ARG = ${PROFILE_ARG}
ENV APP_NAME = projeto-controle.jar

COPY --from=build /app/target/*.jar /app/quiosq.jar
WORKDIR /app
EXPOSE 8080
CMD java -jar quiosq.jar




