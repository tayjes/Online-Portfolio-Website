# Use Eclipse Temurin Java 25 JDK
FROM eclipse-temurin:25-jdk
 
# Set working directory
WORKDIR /app
 
# Copy the JAR file into the container
COPY target/online_portfolio_website-0.0.1-SNAPSHOT.jar app.jar
 
# Expose the Spring Boot port
EXPOSE 8081
 
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]