FROM gradle:8.13.0-jdk21-ubi-minimal as builder
WORKDIR /app
COPY . .
RUN gradle build -x test

FROM eclipse-temurin:21.0.6_7-jre-ubi9-minimal
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=prod
ENV PUBLIC_FQDN=$COOLIFY_FQDN
ENV ARTIFACTORY_PATH=build/libs/*.jar
COPY --from=builder /app/build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 8080
