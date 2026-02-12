FROM gradle:8.6-jdk21 AS build
WORKDIR /app

COPY gradle gradle
COPY gradlew build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle \
    ./gradlew dependencies --no-daemon

COPY domain domain
COPY application application
COPY backend backend

RUN --mount=type=cache,target=/home/gradle/.gradle \
    ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd -r -u 1001 spring
USER spring

COPY --from=build /app/backend/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
