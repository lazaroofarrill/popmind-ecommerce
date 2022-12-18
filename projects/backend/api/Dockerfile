#build
FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle settings.gradle.kts /home/gradle/src/
COPY --chown=gradle:gradle gradle.properties /home/gradle/src/
COPY --chown=gradle:gradle projects/backend/api /home/gradle/src/projects/backend/api
WORKDIR /home/gradle/src
RUN gradle :project:backend:api:buildFatJar --no-daemon


#deploy
FROM openjdk:17-jdk-slim
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/projects/backend/api/build/libs/*.jar /app/api.jar
ENTRYPOINT java -jar /app/api.jar
