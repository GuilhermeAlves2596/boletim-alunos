FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml

RUN chmod +x mvnw

RUN ./mvnw -B -ntp dependency:go-offline

COPY src src

RUN ./mvnw -B -ntp package -DskipTests

FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=/app/target/*.jar
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]