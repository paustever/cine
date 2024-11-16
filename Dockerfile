# Stage 1: Build stage using OpenJDK 21
FROM openjdk:21-jdk AS build
WORKDIR /app

# Copy necessary files
COPY pom.xml .
COPY src src
COPY mvnw .
COPY .mvn .mvn

# Set execution permission and build the application
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime stage
FROM openjdk:21-jdk
WORKDIR /app
VOLUME /tmp

# Copy the JAR file from the build stage and rename it to app.jar
COPY --from=build /app/target/cine-1.0.0 /app/app.jar

# Start the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080