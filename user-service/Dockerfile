FROM gradle:9.7.1-jdk25 AS build
WORKDIR /workspace

COPY settings.gradle build.gradle ./
COPY gradle ./gradle
COPY gradlew ./
COPY user-service ./user-service
COPY account-service ./account-service

RUN chmod +x gradlew \
    && ./gradlew :user-service:bootJar --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /workspace/user-service/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]