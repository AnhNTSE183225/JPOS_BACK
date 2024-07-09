# Use the official Maven image to build the project
FROM maven:3.9.1 AS build

# Install JDK 21
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    apt-get clean;

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the rest of the application code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK image for the runtime environment
FROM eclipse-temurin:21-jre

# Set the working directory in the container
WORKDIR /app

# Copy the built application from the previous stage
COPY --from=build /app/target/*.jar ./app.jar

# Expose port 8080
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
