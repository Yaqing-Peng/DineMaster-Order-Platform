# Use the base image with OpenJDK 17
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the image
COPY target/springboot-web-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application's port
EXPOSE 9090

# Command to start the application
CMD ["java", "-jar", "app.jar"]


